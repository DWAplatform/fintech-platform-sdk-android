package com.fintechplatform.android

import android.content.Context
import com.android.volley.toolbox.Volley
import com.fintechplatform.android.account.balance.BalanceBuilder
import com.fintechplatform.android.account.financialdata.FinancialDataBuilder
import com.fintechplatform.android.api.volley.VolleyRequestProvider
import com.fintechplatform.android.api.volley.VolleyRequestQueueProvider
import com.fintechplatform.android.card.PaymentCardBuilder
import com.fintechplatform.android.card.client.api.ClientCardAPI
import com.fintechplatform.android.card.client.api.ClientCardRestAPI
import com.fintechplatform.android.card.helpers.JSONHelper
import com.fintechplatform.android.card.helpers.PaymentCardHelper
import com.fintechplatform.android.card.helpers.SanityCheck
import com.fintechplatform.android.db.PlatformDB
import com.fintechplatform.android.enterprise.EnterpriseBuilder
import com.fintechplatform.android.iban.IBANBuilder
import com.fintechplatform.android.log.Log
import com.fintechplatform.android.payin.PayInBuilder
import com.fintechplatform.android.payout.PayOutBuilder
import com.fintechplatform.android.profile.ProfileBuilder
import com.fintechplatform.android.qrtransfer.QrTransferBuilder
import com.fintechplatform.android.sct.SctBuilder
import com.fintechplatform.android.secure3d.Secure3DBuilder
import com.fintechplatform.android.transactions.TransactionsBuilder
import com.fintechplatform.android.transfer.TransferBuilder
import com.raizlabs.android.dbflow.config.FlowManager

class FintechPlatform {

    companion object {
        @Volatile private var cardAPIInstance: ClientCardAPI? = null
        //@Volatile public var payinInstance: PayIn? = null

        /**
         * Initialize FintechPlatform
         */
        fun initialize(context: Context) {
            FlowManager.init(context)
            PlatformDB.init()
        }
//
//        fun deleteEverything() {
//            PlatformDB.deleteEverything()
//        }

        /**
         * Factory method to get ClientCardAPI object
         */
        fun getCardAPI(hostName: String, sandbox: Boolean, context: Context): ClientCardAPI =
                cardAPIInstance ?: synchronized(this) {
                    cardAPIInstance ?: buildCardAPI(hostName,
                            context,
                            sandbox).also {
                        cardAPIInstance = it }
                }

        private fun buildCardAPI(hostName: String, context: Context, sandbox: Boolean): ClientCardAPI {

            return ClientCardAPI(ClientCardRestAPI(hostName,
                    VolleyRequestQueueProvider(Volley.newRequestQueue(context)),
                    VolleyRequestProvider(),
                    JSONHelper(),
                    sandbox), Log(), PaymentCardHelper(SanityCheck()))
        }

        fun buildPayIn() : PayInBuilder {
            return PayInBuilder()
        }

        fun buildBalance(): BalanceBuilder {
            return BalanceBuilder()
        }

        fun build3DSecure(): Secure3DBuilder {
            return Secure3DBuilder()
        }

        fun buildPayOut(): PayOutBuilder {
            return PayOutBuilder()
        }

        fun buildPaymentCardBuilder(): PaymentCardBuilder {
            return PaymentCardBuilder()
        }

        fun buildIBAN(): IBANBuilder {
            return IBANBuilder()
        }

        fun buildTransactions(): TransactionsBuilder {
            return TransactionsBuilder()
        }

        fun buildProfile(): ProfileBuilder {
            return ProfileBuilder()
        }

        fun buildEnterpriseData(): EnterpriseBuilder {
            return EnterpriseBuilder()
        }

        fun buildFinancialData(): FinancialDataBuilder {
            return FinancialDataBuilder()
        }

        fun buildTransfer(): TransferBuilder {
            return TransferBuilder()
        }

        fun buildSct(): SctBuilder {
            return SctBuilder()
        }

        fun buildQr(): QrTransferBuilder {
            return QrTransferBuilder()
        }
    }

}

