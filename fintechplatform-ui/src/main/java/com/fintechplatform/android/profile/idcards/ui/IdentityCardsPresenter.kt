package com.fintechplatform.android.profile.idcards.ui

import android.content.Intent
import android.graphics.Bitmap
import com.fintechplatform.android.images.ImageHelper
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.net.NetHelper
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.documents.DocumentsPersistanceDB
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.fintechplatform.android.profile.models.UserDocuments
import java.util.*
import javax.inject.Inject

class IdentityCardsPresenter @Inject constructor(val view: IdentityCardsContract.View,
                                                 val api: ProfileAPI,
                                                 val configuration: DataAccount,
                                                 val dbDocumentsHelper: DocumentsPersistanceDB,
                                                 val usersPersistanceDB: UsersPersistanceDB,
                                                 val imageHelper: ImageHelper): IdentityCardsContract.Presenter {

    var photosBase64 = arrayOfNulls<String?>(2)
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

            it.pages?.let { docs ->
                photosBase64 = docs.toTypedArray()
                photosBase64[0]?.let { view.setFrontImage(imageHelper.bitmapImageView(it)) }
                photosBase64[1]?.let { view.setBackImage(imageHelper.bitmapImageView(it)) }
                return true
            }?: return false

        }?: false
    }

    override fun onRefresh() {
        if(index >= 0) {
            photosBase64[0]?.let { view.setFrontImage(imageHelper.bitmapImageView(it)) }
            photosBase64[1]?.let { view.setBackImage(imageHelper.bitmapImageView(it)) }
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
                "IDENTITY_CARD",
                photosBase64,
                idempDocs) { optDocs, opterror ->

            view.hideWaiting()

            if(opterror != null) {
                handleErrors(opterror)
                return@documents
            }

            if(optDocs == null) {
                return@documents
            }

            val userDocuments = UserDocuments(optDocs,"IDENTITY_CARD", photosBase64.toList())
            view.enableConfirmButton(false)
            dbDocumentsHelper.replaceDocuments(userDocuments)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        if(photosBase64.filterNotNull().size == 2){
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
        val photoBase64 = imageHelper.resizeBitmapViewCardId(bitmap)
        photosBase64[index] = photoBase64
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