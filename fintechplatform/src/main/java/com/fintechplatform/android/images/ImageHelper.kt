package com.fintechplatform.android.images

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.fintechplatform.android.R
import java.io.ByteArrayOutputStream

class ImageHelper {
    fun bitmapImageView(imageBase64: String): Bitmap? {
        val decodedString = Base64.decode(imageBase64, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }

    fun setImageViewUser(view: ImageView, optimageBase64: String?) {
        optimageBase64?.let { imageBase64 ->
            val optbiu = bitmapImageView(imageBase64)
            optbiu?.let { biu ->
                view.setImageBitmap(biu)
            }
        } ?: view.setImageResource(R.drawable.ic_account_circle_black)
    }

    fun resizeBitmapViewUser(bitmap: Bitmap): String {
        val resized = if (bitmap.width > bitmap.height) {
            Bitmap.createScaledBitmap(bitmap, (java.lang.Float.valueOf(bitmap.width.toFloat())!! / java.lang.Float.valueOf(bitmap.height.toFloat())!! * 256f).toInt(), 256, true)
        } else {
            Bitmap.createScaledBitmap(bitmap, 256, (java.lang.Float.valueOf(bitmap.height.toFloat())!! / java.lang.Float.valueOf(bitmap.width.toFloat())!! * 256f).toInt(), true)
        }

        bitmap.recycle()
        val bitmapcropped = Bitmap.createBitmap(resized, (resized.width - 256) / 2, (resized.height - 256) / 2, 256, 256)
        resized.recycle()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmapcropped.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream)
        val photoBase64 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
        return photoBase64
    }

    fun resizeBitmapViewCardId(bitmap: Bitmap): String {
        val resized = if (bitmap.width > bitmap.height) {
            Bitmap.createScaledBitmap(bitmap, 512,(java.lang.Float.valueOf(bitmap.height.toFloat())!! / java.lang.Float.valueOf(bitmap.width.toFloat())!! * 512f).toInt(), true)
        } else {
            Bitmap.createScaledBitmap(bitmap, (java.lang.Float.valueOf(bitmap.width.toFloat())!! / java.lang.Float.valueOf(bitmap.height.toFloat())!! * 512f).toInt(), 512,true)
        }

        bitmap.recycle()

        val byteArrayOutputStream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream)
        resized.recycle()
        val photoBase64 = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
        return photoBase64
    }
}