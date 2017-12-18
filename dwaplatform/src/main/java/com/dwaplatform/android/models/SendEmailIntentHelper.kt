package com.dwaplatform.android.models

import android.content.Intent

/**
 * Created by ingrid on 18/12/17.
 */
open class SendEmailIntentHelper {

    fun sendEmailIntent(recipient: Array<String>, subject: String, message: String, intenttitle: String): Intent {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/html"
        i.putExtra(Intent.EXTRA_EMAIL, recipient)
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        i.putExtra(Intent.EXTRA_TEXT, message)
        return Intent.createChooser(i, intenttitle)
    }

}