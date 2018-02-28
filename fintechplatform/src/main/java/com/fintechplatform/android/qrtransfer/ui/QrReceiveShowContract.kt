package com.fintechplatform.android.qrtransfer.ui

import android.graphics.Bitmap

/**
 * Created by ingrid on 18/09/17.
 */
interface QrReceiveShowContract {
    interface View{
        fun setQrImage(bmp: Bitmap)
    }

    interface Presenter{
        fun generateQrCode(transactionId: String?)
    }
}