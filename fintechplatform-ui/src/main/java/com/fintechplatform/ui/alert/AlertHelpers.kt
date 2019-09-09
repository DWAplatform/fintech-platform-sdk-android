package com.fintechplatform.ui.alert

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * Created by tcappellari on 08/12/2017.
 */

/**
 * Alert Dialogs
 */
open class AlertHelpers {

    fun internalError(context: Context) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Errore connessione internet")
        builder.setMessage("Verificare la connessione a internet e riprovare")
        // FIXME commented due to sdk refactor
        //builder.setIcon(R.drawable.ic_warning_black_24dp)
        builder.setPositiveButton(
                "OK"
        ) { dialog, _ -> dialog.cancel() }
        return builder
    }

    fun idempotencyError(context: Context) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Richiesta già inviata")
        builder.setMessage("La richiesta è già stata inviata.")
        // FIXME commented due to sdk refactor
        //builder.setIcon(R.drawable.ic_warning_black_24dp)
        builder.setPositiveButton(
                "OK"
        ) { dialog, _ -> dialog.cancel() }
        return builder
    }

    fun genericError(context: Context, title: String, message: String) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        // FIXME commented due to sdk refactor
        //builder.setIcon(R.drawable.ic_warning_black_24dp)
        builder.setPositiveButton(
                "OK"
        ) { dialog, _ -> dialog.cancel() }
        return builder
    }

    fun confirmCancelGeneric(context: Context,
                             title: String,
                             message: String,
                             handlerOk: (DialogInterface, Int) -> Unit,
                             handlerCancel: (DialogInterface, Int) -> Unit): AlertDialog.Builder {

        val builder = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                // FIXME commented due to sdk refactor
                //.setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton("Conferma", handlerOk)
                .setNegativeButton("Annulla", handlerCancel)
        return builder
    }

    fun warningGeneric(context: Context, title: String, message: String) {
        val builder = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        "OK"
                ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    fun successGeneric(context: Context, title: String, handlerOk: (DialogInterface, Int) -> Unit){
        val builder = android.support.v7.app.AlertDialog.Builder(context)
                // FIXME commented due to sdk refactor
                // .setView(R.layout.dialog_success)
                .setTitle(title)
                .setPositiveButton(
                        "OK",
                        handlerOk)

        builder.show()
    }

    fun qrCodeError(context: Context, handlerOk: (DialogInterface, Int) -> Unit){
        val builder = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("Verificare che il qrcode sia stato emesso da Fintech Platform")
                // FIXME commented due to sdk refactor
                // .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton("OK",
                        handlerOk)

        builder.show()
    }

    fun tokenExpired(context: Context, handlerOk: (DialogInterface, Int) -> Unit) {
        val builder = android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("Sessione scaduta")
                .setMessage("rifare l'accesso")
                .setPositiveButton("Ok", handlerOk)

        builder.show()
    }

}
