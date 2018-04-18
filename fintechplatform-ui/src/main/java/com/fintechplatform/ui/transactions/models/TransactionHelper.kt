package com.fintechplatform.ui.transactions.models

import com.fintechplatform.api.money.Money
import com.fintechplatform.ui.money.MoneyHelper
import com.fintechplatform.api.transactions.models.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionHelper @Inject constructor(val moneyHelper: MoneyHelper) {

    private fun convertDate(date: String?): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        if (date.isNullOrBlank()) {
            return ""
        } else {
            val d = sdf.parse(date)
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            return formatter.format(d)
        }
    }

    fun transactionItem(t: TransactionResponse): TransactionItem? {
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        val twhen = convertDate(t.creationDate)
        val timeInMilliseconds = serverDateFormat.parse(t.creationDate).time
        val operationtype = t.operationType

        val transactionitem: TransactionItem?

        when(operationtype) {
            "LINKED_CARD_CASH_IN" -> {

                val creditedfunds = t.creditedFunds ?: return null

                val mhString = moneyHelper.toString(Money(creditedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Ricarica",
                        "Carta di Pagamento",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)

            }
            "LINKED_BANK_CASH_OUT" -> {
                val debitedfunds = t.debitedFunds ?: return null
                val mhString = moneyHelper.toString(Money(-debitedfunds))

                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Prelievo",
                        "Conto Bancario",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }
            "TRANSFER_SEND" -> {
                val t_debitedfunds = t.debitedFunds ?: return null
                val creditedFullName = t.creditedName?: return null

                val mhString = moneyHelper.toString(Money(-t_debitedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Trasferimento",
                        creditedFullName,
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }

            "TRANSFER_RECEIVE" -> {
                val t_creditedfunds = t.creditedFunds ?: return null
                val debitedFullName = t.debitedName ?: return null
                val mhString = moneyHelper.toString(Money(t_creditedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Trasferimento",
                        debitedFullName,
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)

            }

            "SCT_PAYMENT" -> {
                val t_debitedfunds = t.debitedFunds ?: return null

                val mhString = moneyHelper.toString(Money(-t_debitedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Bonifico interno",
                        "Conto Bancario",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }

            "BANK_WIRE_CASH_IN" -> {
                val creditedfunds = t.creditedFunds ?: return null

                val mhString = moneyHelper.toString(Money(creditedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Ricarica",
                        "Conto Bancario",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }
            "EXTERNAL_BANK_TRANSACTION" -> {

                val creditedfunds = t.creditedFunds ?: return null

                val mhString = moneyHelper.toString(Money(creditedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "Movimento esterno",
                        "Conto Sella",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }
            "PAGO_PA" ->{
                val t_debitedfunds = t.debitedFunds ?: return null

                val mhString = moneyHelper.toString(Money(-t_debitedfunds))
                transactionitem = TransactionItem(
                        t.transactionIds,
                        t.accountId,
                        "PagoPA",
                        "Ente pubblico",
                        t.message,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.error)
            }
//            "Purchase_M1" -> {
//                val t_debiteduserid = t.debiteduserid ?: return null
//                val t_debitedfunds = t.debitedfunds ?: return null
//
//                val t_clientname = t.clientname ?: return null
//                val t_brokerlegaluserid = t.brokerlegaluserid ?: return null
//
//                val t_brokerfunds = t.brokerfunds ?: return null
//
//                val mdebitedfundsString = moneyHelper.toString(Money(-t_debitedfunds.toLong()))
//                val mbrokerfundsString = moneyHelper.toString(Money(t_brokerfunds.toLong()))
//
//                if (userid == t_debiteduserid) {
//                    transactionitem = TransactionItem(
//                            t.id,
//                            "Pagamento ACQUISTO",
//                            t_clientname,
//                            t.message,
//                            mdebitedfundsString,
//                            twhen,
//                            timeInMilliseconds,
//                            t.status,
//                            t.resultcode)
//
//                } else if (userid == t_brokerlegaluserid) {
//
//                    transactionitem = TransactionItem(
//                            t.id,
//                            "Vendita",
//                            t_clientname,
//                            t.message,
//                            mbrokerfundsString,
//                            twhen,
//                            timeInMilliseconds,
//                            t.status,
//                            t.resultcode)
//                }

            else ->
                transactionitem = null
        }
        return transactionitem
    }

}