package com.dwaplatform.android.enterprise.documents.ui

import android.content.Intent

interface EnterpriseDocumentsContract {
    interface View {
        fun setAbortText()
        fun setNumberPages(number: Int)
        fun enableButtons(isEnable: Boolean)
        fun checkCameraPermission()
        fun goToCamera()
        fun showTokenExpiredWarning()
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
        fun onCameraClick()
        fun onPictureTaken(optData: Intent?, index: Int)
    }
}