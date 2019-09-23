package com.fintechplatform.ui.enterprise.documents

import android.content.Intent
import android.graphics.Bitmap
import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseDocs
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.enterprise.db.documents.EnterpriseDocumentsPersistanceDB
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import java.util.*
import kotlin.collections.ArrayList

class EnterpriseDocumentsPresenter constructor(val view: EnterpriseDocumentsContract.View,
                                               val api: EnterpriseAPI,
                                               val configuration: DataAccount,
                                               val dbDocuments: EnterpriseDocumentsPersistanceDB,
                                               val imageHelper: ImageHelper): EnterpriseDocumentsContract.Presenter {

    var photosBase64 = arrayListOf<String?>()
    var idempotencyIDcard: String? = null
    var token: String? = null

    override fun initializate() {
        view.checkCameraPermission()

        if(!loadFromDB()) {
            reloadFromServer()
        }
        idempotencyIDcard = UUID.randomUUID().toString()
    }

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

    override fun onRefresh() {
        view.setNumberPages(photosBase64.size)
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
                "IDENTITY_PROOF",
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


            val pages = ArrayList<String?>()
            for (i in 0 until photosBase64.size) {
                photosBase64[i]?.let { pages.add(it) }
            }

            val documents = EnterpriseDocs(optDocs,"IDENTITY_PROOF", pages)
            view.enableConfirmButton(false)
            dbDocuments.replaceDocuments(documents)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        if(photosBase64.isNotEmpty()){
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }


    override fun onPictureTaken(optData: Intent?, index: Int) {
        val data = optData?: return
        val bitmap = data.extras["data"] as Bitmap
        val photoBase64 = imageHelper.resizeBitmapViewCardId(bitmap)
        photosBase64.add(photoBase64)
        refreshConfirmButton()
    }

    override fun onInsertPages() {
        view.goToCamera()
    }

    fun reloadFromServer() {

        api.getDocuments(configuration.accessToken, configuration.ownerId, configuration.tenantId) {
            etpsDocs: ArrayList<EnterpriseDocs?>?, opterror: Exception? ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getDocuments
            }

            if (etpsDocs == null) {
                return@getDocuments
            }

            for (i in 0 until etpsDocs.size) {
                etpsDocs[i]?.let {
                    dbDocuments.saveDocuments(it)
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
                view.showGenericError()
        }
    }
}