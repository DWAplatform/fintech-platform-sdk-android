package com.fintechplatform.ui.profile.idcards.ui

import android.content.Intent
import android.graphics.Bitmap
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.api.profile.models.UserDocuments
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDB
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class IdentityCardsPresenter @Inject constructor(val view: IdentityCardsContract.View,
                                                 val api: ProfileAPI,
                                                 val configuration: DataAccount,
                                                 val dbDocumentsHelper: DocumentsPersistanceDB,
                                                 val usersPersistanceDB: UsersPersistanceDB,
                                                 val imageHelper: ImageHelper): IdentityCardsContract.Presenter {

    var photosByteArray = arrayListOf<ByteArray>()
    var imagesBase64 = arrayListOf<String?>()
    var index = -1
    var idempotencyIDcard: String? = null
    var token: String? = null

    override fun initializate() {
        view.checkCameraPermission()

        loadFromDB()
        reloadFromServer()

        idempotencyIDcard = UUID.randomUUID().toString()
    }

    private fun loadFromDB(): Boolean {
        return dbDocumentsHelper.getDocuments()?.let {
            it.imagesBase64?.let { docs ->
                this.imagesBase64 = ArrayList(docs)
                view.setFrontImage(imageHelper.bitmapImageView(docs[0]))
                view.setBackImage(imageHelper.bitmapImageView(docs[1]))
                return true
            }?: return false

        }?: false
    }

    override fun onRefresh() {
        if(index >= 0) {
            imagesBase64[0]?.let { view.setFrontImage(imageHelper.bitmapImageView(it)) }
            imagesBase64[1]?.let { view.setBackImage(imageHelper.bitmapImageView(it)) }
        }
    }

    override fun onAbort() {
        view.goBack()
    }

    override fun onConfirm() {
        view.showWaiting()

        val idempDocs = this.idempotencyIDcard ?: return

        api.documents(
                configuration.accessToken,
                configuration.ownerId,
                configuration.tenantId,
                null, //todo decide which filename
                "IDENTITY_CARD", //todo doctype must not be hard coded
                photosByteArray,
                idempDocs) { optDocs, opterror ->

            view.hideWaiting()

            if(opterror != null) {
                handleErrors(opterror)
                return@documents
            }

            if(optDocs == null) {
                return@documents
            }

            view.enableConfirmButton(false)
            val userDocuments = optDocs.copy(imagesBase64 = imagesBase64.filterNotNull())
            dbDocumentsHelper.replaceDocuments(userDocuments)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        if(photosByteArray.size == 2){
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }

    override fun onCameraFrontClick() {
        view.goToCameraFront()
    }

    override fun onCameraBackClick() {
        view.goToCameraBack()
    }

    override fun onPictureTaken(optData: Intent?, index: Int) {
        val data = optData?: return
        val bitmap = data.extras["data"] as Bitmap
        val photoByteArray = imageHelper.bitmapToByteArray(bitmap)
        photosByteArray[index] = photoByteArray
        imagesBase64[index] = imageHelper.resizeBitmapViewCardId(bitmap)
        refreshConfirmButton()
        this.index = index
    }

    fun reloadFromServer() {

        api.getDocuments(configuration.accessToken, configuration.ownerId, configuration.tenantId) {
            userDocs: List<UserDocuments?>?, opterror: Exception? ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getDocuments
            }

            if (userDocs == null) {
                return@getDocuments
            }

            for (i in 0 until userDocs.size) {
                userDocs[i]?.let {
                    dbDocumentsHelper.saveDocuments(it)
                }
            }

            loadFromDB()
        }
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                return
        }
    }
}