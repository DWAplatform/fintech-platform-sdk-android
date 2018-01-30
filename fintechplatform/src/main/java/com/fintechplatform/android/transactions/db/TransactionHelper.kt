package com.fintechplatform.android.transactions.db

import com.fintechplatform.android.money.Money
import com.fintechplatform.android.money.MoneyHelper
import com.fintechplatform.android.transactions.models.TransactionItem
import com.fintechplatform.android.transactions.models.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionHelper @Inject constructor(val moneyHelper: MoneyHelper) {

    fun transactionItem(t: TransactionResponse,
                        userid: String): TransactionItem? {

        val timeInMilliseconds = t.creationdate
        val dateTime = Date(timeInMilliseconds)

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val twhen = formatter.format(dateTime)

        val operationtype = t.operationtype

        val transactionitem: TransactionItem?

        when(operationtype) {
            "Payin" -> {

                val creditedfunds = t.creditedfunds ?: return null

                val mhString = moneyHelper.toString(Money(creditedfunds.toLong()))
                transactionitem = TransactionItem(
                        t.id,
                        "Ricarica",
                        "Carta di Credito",
                        null,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.resultcode)

            }
            "Payout" -> {
                val debitedfunds = t.debitedfunds ?: return null
                val mhString = moneyHelper.toString(Money(-debitedfunds.toLong()))

                transactionitem = TransactionItem(
                        t.id,
                        "Prelievo",
                        "Conto Bancario",
                        null,
                        mhString,
                        twhen,
                        timeInMilliseconds,
                        t.status,
                        t.resultcode)
            }
            "Transfer" -> {
                val t_crediteduserid = t.crediteduserid ?: return null
                val t_debiteduserid = t.debiteduserid ?: return null
                val t_creditedfunds = t.creditedfunds ?: return null
                val t_debitedfunds = t.debitedfunds ?: return null
                val t_creditedfullname = t.crediteduserfullname ?: return null
                val t_debitedfullname = t.debiteduserfullname ?: return null

                if (userid == t_debiteduserid) {
                    val mhString = moneyHelper.toString(Money(-t_debitedfunds.toLong()))
                    transactionitem = TransactionItem(
                            t.id,
                            "Trasferimento",
                            t_creditedfullname,
                            t.message,
                            mhString,
                            twhen,
                            timeInMilliseconds,
                            t.status,
                            t.resultcode)
                } else if (userid == t_crediteduserid) {
                    val mhString = moneyHelper.toString(Money(t_creditedfunds.toLong()))
                    transactionitem = TransactionItem(
                            t.id,
                            "Trasferimento",
                            t_debitedfullname,
                            t.message,
                            mhString,
                            twhen,
                            timeInMilliseconds,
                            t.status,
                            t.resultcode)
                } else {
                    transactionitem = null
                }
            }
            "Purchase_M1" -> {
                val t_debiteduserid = t.debiteduserid ?: return null
                val t_debitedfunds = t.debitedfunds ?: return null

                val t_clientname = t.clientname ?: return null
                val t_brokerlegaluserid = t.brokerlegaluserid ?: return null

                val t_brokerfunds = t.brokerfunds ?: return null

                val mdebitedfundsString = moneyHelper.toString(Money(-t_debitedfunds.toLong()))
                val mbrokerfundsString = moneyHelper.toString(Money(t_brokerfunds.toLong()))

                if (userid == t_debiteduserid) {
                    transactionitem = TransactionItem(
                            t.id,
                            "Pagamento ACQUISTO",
                            t_clientname,
                            t.message,
                            mdebitedfundsString,
                            twhen,
                            timeInMilliseconds,
                            t.status,
                            t.resultcode)

                } else if (userid == t_brokerlegaluserid) {

                    transactionitem = TransactionItem(
                            t.id,
                            "Vendita",
                            t_clientname,
                            t.message,
                            mbrokerfundsString,
                            twhen,
                            timeInMilliseconds,
                            t.status,
                            t.resultcode)
                } else {
                    transactionitem = null
                }
            }
            else ->
                transactionitem = null
        }
        return transactionitem
    }

}