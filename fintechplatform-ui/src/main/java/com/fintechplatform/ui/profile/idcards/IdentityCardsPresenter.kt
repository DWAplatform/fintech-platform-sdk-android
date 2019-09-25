package com.fintechplatform.ui.profile.idcards

import android.content.Intent
import android.util.Log
import com.fintechplatform.api.account.kyc.KycAPI
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.api.profile.models.DocType
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDB
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import java.io.*
import java.util.*
import javax.inject.Inject


class IdentityCardsPresenter @Inject constructor(val view: IdentityCardsContract.View,
                                                 val api: ProfileAPI,
                                                 val kycAPI: KycAPI,
                                                 val configuration: DataAccount,
                                                 val dbDocumentsHelper: DocumentsPersistanceDB,
                                                 val usersPersistanceDB: UsersPersistanceDB,
                                                 val imageHelper: ImageHelper): IdentityCardsContract.Presenter {

    var photosByteArray : Array<ByteArray?>? = null
    var imagesBase64 : Array<String?>? = null
    var idempotencyIDcard: String? = null
    var docType: DocType? = null
    var fileName: String? = null

    override fun initializate() {
        view.checkCameraPermission()
        getDocTypes()
        loadFromDB()

        idempotencyIDcard = UUID.randomUUID().toString()
    }

    private fun loadFromDB(): Boolean {
        return dbDocumentsHelper.getDocuments()?.let {
            it.imagesBase64?.let { docs ->
               // this.imagesBase64 = docs
                view.setFrontImage(imageHelper.bitmapImageView(docs[0]))
                view.setBackImage(imageHelper.bitmapImageView(docs[1]))
                return true
            }?: return false

        }?: false
    }

    override fun onRefresh() {
        imagesBase64?.get(0)?.let { view.setFrontImage(imageHelper.bitmapImageView(it)) }
        if (imagesBase64?.size == 2)
            imagesBase64?.get(1)?.let { view.setBackImage(imageHelper.bitmapImageView(it)) }
    }

    override fun onAbort() {
        view.goBack()
    }

    override fun onConfirm() {
        view.showWaiting()

        val idempDocs = this.idempotencyIDcard ?: return
        val docType = this.docType?: return
        val byteArrayList = photosByteArray?.toList()?.filterNotNull()?: return

        api.documents(
                configuration.accessToken,
                configuration.ownerId,
                configuration.tenantId,
                fileName,
                docType,
                byteArrayList,
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
            //dbDocumentsHelper.replaceDocuments(optDocs)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        photosByteArray?.filterNotNull()?.isNotEmpty()?.let(view::enableConfirmButton)
    }

    override fun onCameraFrontClick() {
        view.goToCameraFront()
    }

    override fun onCameraBackClick() {
        view.goToCameraBack()
    }

    override fun onPictureTaken(filePath: File, index: Int, thumbnail: Intent?) {

        // create byte array
        val size = filePath.length().toInt()
        val photoByteArray = ByteArray(size)
        try {
            val buf = BufferedInputStream(FileInputStream(filePath))
            buf.read(photoByteArray, 0, photoByteArray.size)
            buf.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        photosByteArray?.set(index, photoByteArray)
        Log.d("ByteArray size", "${photoByteArray.size}")
        fileName = filePath.name

        Log.d("fileName", fileName)
        // set thumbnail
//        val data = thumbnail?: return
//        val bitmap = data.extras["data"] as Bitmap
//        imagesBase64?.set(index, imageHelper.resizeBitmapViewCardId(bitmap))
//        bitmap.recycle()

        refreshConfirmButton()
    }

    override fun onDocTypeSelected(docType: DocType) {
        this.docType = docType
        refreshConfirmButton()
        when(docType) {
            DocType.IDENTITY_CARD -> {
                view.showFrontAndBack()
                photosByteArray = arrayOfNulls(2)
                imagesBase64 = arrayOfNulls(2)
            }
            DocType.PASSPORT -> {
                view.showOnePicture()
                photosByteArray = arrayOfNulls(1)
                imagesBase64 = arrayOfNulls(1)
            }
            DocType.DRIVING_LICENCE -> {
                view.showFrontAndBack()
                photosByteArray = arrayOfNulls(2)
                imagesBase64 = arrayOfNulls(2)
            }
        }
    }

    fun getDocTypes() {
        kycAPI.getKycRequired(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId) { optDocTypeList, optError ->
            
            optError?.let { handleErrors(it); return@getKycRequired }
            
            if (optDocTypeList == null) {
                return@getKycRequired
            }

            view.addDocumentTypeSelectable(optDocTypeList)
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