package com.fintechplatform.ui.enterprise.documents

import com.fintechplatform.api.enterprise.models.EnterpriseDocType
import java.io.File

interface EnterpriseDocumentsContract {
    interface View {
        fun setAbortText()
        fun setNumberPages(number: Int)
        fun setDocTypeSelected(docType: EnterpriseDocType)
        fun checkCameraPermission()
        fun goToCamera()
        fun showTokenExpiredWarning()
        fun showGenericError()
        fun enableConfirmButton(isEnable: Boolean)
        fun showWaiting()
        fun hideWaiting()
        fun goBack()
    }

    interface Presenter {
        fun initializate()
        fun onRefresh()
        fun onAbort()
        fun onConfirm()
        fun onInsertPages()
        fun refreshConfirmButton()
        fun onPictureTaken(file: File, index: Int)

    }

    interface Navigation {
        fun backFromEnterpriseDocuments()
    }
}