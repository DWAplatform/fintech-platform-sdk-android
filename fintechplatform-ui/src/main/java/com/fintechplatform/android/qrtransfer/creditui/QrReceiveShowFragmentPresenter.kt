package com.fintechplatform.android.qrtransfer.creditui

import android.graphics.Bitmap
import android.graphics.Color
import com.fintechplatform.android.log.Log

import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

/**
 * Created by ingrid on 18/09/17.
 */
class QrReceiveShowFragmentPresenter constructor(var view: QrReceiveShowContract.View,
                                                 var log: Log): QrReceiveShowContract.Presenter {

    private val TAG = "QrReceiveShowFragment"

    override fun generateQrCode(transactionId: String?) {
        if (transactionId == null)
            return

        try {
            val bitMatrix = QRCodeWriter()
                    .encode(transactionId, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0..width - 1) {
                for (y in 0..height - 1) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            view.setQrImage(bmp)


        } catch (e: WriterException) {
            log.error(TAG, "bitmap error", e)
        }
    }
}