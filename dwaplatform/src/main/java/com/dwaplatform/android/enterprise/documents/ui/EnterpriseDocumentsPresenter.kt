package com.dwaplatform.android.enterprise.documents.ui

import android.content.Intent
import android.graphics.Bitmap
import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.enterprise.api.EnterpriseAPI
import com.dwaplatform.android.enterprise.db.documents.EnterpriseDocumentsPersistanceDB
import com.dwaplatform.android.enterprise.models.EnterpriseDocs
import com.dwaplatform.android.enterprise.models.EnterpriseDocumentPages
import com.dwaplatform.android.images.ImageHelper
import com.dwaplatform.android.models.DataAccount
import java.util.*
import javax.inject.Inject
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

        dbDocuments.getDocuments(configuration.accountId)?.let {
            it.pages?.let { pages ->
                for (i in 0 until pages.size) {
                    photosBase64.add(pages[i]?.page)
                }
                view.setNumberPages(photosBase64.size)
            }

        }?: askDocuments()

        idempotencyIDcard = UUID.randomUUID().toString()
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
                configuration.userId,
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


            val pages = ArrayList<EnterpriseDocumentPages?>()
            for (i in 0 until photosBase64.size) {
                photosBase64[i]?.let { pages.add(EnterpriseDocumentPages(configuration.accountId, it)) }
            }

            val documents = EnterpriseDocs(configuration.accountId,"IDENTITY_PROOF", pages)
            view.enableConfirmButton(false)
            dbDocuments.replaceDocuments(documents)
            view.goBack()
        }
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        if(photosBase64.filterNotNull().size >= 2){
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

    fun askDocuments() {

        api.getDocuments(configuration.accessToken, configuration.userId) {
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

            initializate()
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