package com.fintechplatform.android.transactions.db

import com.fintechplatform.android.money.Money
import com.fintechplatform.android.money.MoneyHelper
import com.fintechplatform.android.transactions.models.TransactionItem
import com.fintechplatform.android.transactions.models.TransactionResponse
import java.text.SimpleDateFormat
import javax.inject.Inject

class TransactionHelper @Inject constructor(val moneyHelper: MoneyHelper) {

    fun transactionItem(t: TransactionResponse): TransactionItem? {
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd")
//        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
//        val twhen = formatter.format(serverDateFormat.parse(t.creationdate))

        val twhen = t.creationdate
        val timeInMilliseconds = serverDateFormat.parse(twhen).time
        val operationtype = t.operationtype

        val transactionitem: TransactionItem?

        when(operationtype) {
            "LINKED_CARD_CASH_IN" -> {

                val creditedfunds = t.creditedfunds ?: return null

                val mhString = moneyHelper.toString(Money(creditedfunds))
                transactionitem = TransactionItem(
                        t.transactionids,
                        "Ricarica",
                        "Carta di Pagamento",
                        null,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status)

            }
            "LINKED_BANK_CASH_OUT" -> {
                val debitedfunds = t.debitedfunds ?: return null
                val mhString = moneyHelper.toString(Money(-debitedfunds))

                transactionitem = TransactionItem(
                        t.transactionids,
                        "Prelievo",
                        "Conto Bancario",
                        null,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status)
            }
            "TRANSFER_SEND" -> {
                val t_debitedfunds = t.debitedfunds ?: return null
                val t_creditedfullname = t.crediteduserfullname ?: return null

                    val mhString = moneyHelper.toString(Money(-t_debitedfunds))
                    transactionitem = TransactionItem(
                            t.transactionids,
                            "Trasferimento",
                            t_creditedfullname,
                            t.message,
                            mhString,
                            twhen,
                            timeInMilliseconds,
                            t.status)
            }

            "TRANSFER_RECEIVE" -> {
                val t_creditedfunds = t.creditedfunds ?: return null
                val t_debitedfullname = t.debiteduserfullname ?: return null

                val mhString = moneyHelper.toString(Money(t_creditedfunds))
                    transactionitem = TransactionItem(
                            t.transactionids,
                            "Trasferimento",
                            t_debitedfullname,
                            t.message,
                            mhString,
                            twhen,
                            timeInMilliseconds,
                            t.status)

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
//                } else {
//                    transactionitem = null
//                }
//            }
            else ->
                transactionitem = null
        }
        return transactionitem
    }

}