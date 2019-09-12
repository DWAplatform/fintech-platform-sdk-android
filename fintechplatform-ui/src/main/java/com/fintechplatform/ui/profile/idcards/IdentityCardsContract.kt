package com.fintechplatform.ui.profile.idcards

import android.content.Intent
import android.graphics.Bitmap
import com.fintechplatform.api.profile.models.DocType

interface IdentityCardsContract {
    interface View {
        fun addDocumentTypeSelectable(list: List<DocType>)
        fun showFrontAndBack()
        fun showOnePicture()
        fun setAbortText()
        fun setFrontImage(front: Bitmap?)
        fun setBackImage(back: Bitmap?)
        fun enableButtons(isEnable: Boolean)
        fun checkCameraPermission()
        fun goToCameraFront()
        fun goToCameraBack()
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
        fun onPictureTaken(optData: Intent?, index: Int)
        fun refreshConfirmButton()
        fun onCameraFrontClick()
        fun onCameraBackClick()
        fun onDocTypeSelected(docType: DocType)
    }

    interface Navigation {
        fun goBackFromIdCards()
    }
}