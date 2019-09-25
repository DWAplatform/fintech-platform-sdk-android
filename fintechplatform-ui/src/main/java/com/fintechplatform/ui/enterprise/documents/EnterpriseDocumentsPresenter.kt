package com.fintechplatform.ui.enterprise.documents

import android.util.Log
import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseDocType
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.enterprise.db.documents.EnterpriseDocumentsPersistanceDB
import com.fintechplatform.ui.enterprise.documents.dialog.EnterpriseDocTypeDialog
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import java.io.*
import java.util.*

class EnterpriseDocumentsPresenter constructor(val view: EnterpriseDocumentsContract.View,
                                               val api: EnterpriseAPI,
                                               val configuration: DataAccount,
                                               val dbDocuments: EnterpriseDocumentsPersistanceDB,
                                               val imageHelper: ImageHelper): EnterpriseDocumentsContract.Presenter, EnterpriseDocTypeDialog.DocTypePicker  {

    var photosByteArray = arrayListOf<ByteArray?>()
    //var photosBase64 = arrayListOf<String?>()
    var idempotencyIDcard: String? = null
    var docType: EnterpriseDocType? = null
    var fileName: String? = null

    override fun initializate() {
        view.checkCameraPermission()
        //loadFromDB()
        idempotencyIDcard = UUID.randomUUID().toString()
    }
/*
    private fun loadFromDB(): Boolean {
        return dbDocuments.getDocuments(configuration.ownerId)?.let {
            it.pages?.let { pages ->
                for (i in 0 until pages.size) {
                    photosBase64.add(pages[i])
                }
                view.setNumberPages(photosBase64.size)
                return true
            }?: return false
        }?: false
    }
*/
    override fun onRefresh() {
        view.setNumberPages(photosByteArray?.size)
    }

    override fun onAbort() {
        view.goBack()
    }

    override fun onPickDocType(docType: EnterpriseDocType) {
        this.docType = docType
        view.setDocTypeSelected(docType)
        refreshConfirmButton()
    }

    override fun onConfirm() {
        view.showWaiting()

        val idempDocs = this.idempotencyIDcard ?: return

        val docType = this.docType?: return
        val byteArrayList = photosByteArray.filterNotNull()
        
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

            Log.d("onConfirm response", optDocs)
//
//            val pages = ArrayList<String?>()
//            for (i in 0 until photosBase64.size) {
//                photosBase64[i]?.let { pages.add(it) }
//            }
//
//            val documents = EnterpriseDocs(optDocs,"IDENTITY_PROOF", pages)
            view.enableConfirmButton(false)
//            dbDocuments.replaceDocuments(documents)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        docType?.let {
            if(photosByteArray.isNotEmpty()){
                view.enableConfirmButton(true)
            }
        }?: view.enableConfirmButton(false)
    }


    override fun onPictureTaken(file: File, index: Int) {
        // create byte array
        val size = file.length().toInt()
        val photoByteArray = ByteArray(size)
        try {
            val buf = BufferedInputStream(FileInputStream(file))
            buf.read(photoByteArray, 0, photoByteArray.size)
            buf.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        photosByteArray.add(photoByteArray)
        Log.d("ByteArray size", "${photoByteArray.size}")
        fileName = file.name

        Log.d("fileName", fileName)

//        val data = optData?: return
//        val bitmap = data.extras["data"] as Bitmap
//        val photoBase64 = imageHelper.resizeBitmapViewCardId(bitmap)
//        photosBase64.add(photoBase64)
        refreshConfirmButton()
    }

    override fun onInsertPages() {
        view.goToCamera()
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showGenericError()
        }
    }
}