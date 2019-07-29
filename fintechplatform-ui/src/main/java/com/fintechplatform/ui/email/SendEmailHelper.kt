package com.fintechplatform.ui.email

import android.content.Context
import android.text.Html
import androidx.core.content.ContextCompat
import com.fintechplatform.ui.transactions.models.TransactionItem
import javax.inject.Inject

open class SendEmailHelper @Inject constructor(val sendemail: SendEmailIntentHelper) {

    data class EmailMessage(val subject: String, val body: String)

    val emailsupport = "tcappellari@nabertech.com"

    open fun sendEmail(context: Context, emailmsg: EmailMessage): Boolean {
        val htmlbody = Html.fromHtml(emailmsg.body).toString()
        val i = sendemail.sendEmailIntent(arrayOf(emailsupport),
                emailmsg.subject, htmlbody, "Invio email")
        try {
            ContextCompat.startActivity(context, i, null)
            return true
        } catch (ex: android.content.ActivityNotFoundException) {
            return false
        }
    }

    fun sendTransactionHelp(context: Context, transaction: TransactionItem): Boolean {
        return sendEmail(context, transactionHelp(transaction))

    }

    fun transactionHelp(transaction: TransactionItem): EmailMessage {
        val subject = "Segnalazione problema transazione"
        val body = "<p>Buongiorno Fintech platform,</p>" +
                "<p>Vorrei avere informazioni relative " +
                "alla transazione ${transaction.id} del ${transaction.twhen}</p>" +
                "<p>...</p>" +
                "<p>Cordiali saluti</p>"
        return EmailMessage(subject, body)
    }

    fun sendGenericHelp(context: Context): Boolean {
        return sendEmail(context, genericHelp())
    }

    fun genericHelp(): EmailMessage {
        val subject = "Richiesta Informazioni"
        val body = "<p>Buongiorno Fintech Platform,</p>" +
                "<p>Vorrei alcune informazioni riguardanti la vostra app</p>" +
                "<p>...</p>" +
                "<p>Cordiali saluti</p>"
        return EmailMessage(subject, body)
    }

}