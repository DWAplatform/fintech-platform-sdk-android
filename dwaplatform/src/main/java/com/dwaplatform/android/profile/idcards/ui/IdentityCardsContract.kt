package com.dwaplatform.android.profile.idcards.ui

import android.content.Intent
import android.graphics.Bitmap

/**
 * Created by ingrid on 10/01/18.
 */
interface IdentityCardsContract {
    interface View {
        fun setAbortText()
        fun setFrontImage(front: Bitmap?)
        fun setBackImage(back: Bitmap?)
        fun enableButtons(isEnable: Boolean)
        fun checkCameraPermission()
        fun goToCameraFront()
        fun goToCameraBack()
        fun showCommunicationInternalNetwork()
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
    }
}