package com.fintechplatform.android

import android.content.Context
import com.android.volley.toolbox.Volley
import com.fintechplatform.android.account.balance.BalanceBuilder
import com.fintechplatform.android.account.payinpayoutfinancialdata.PayInPayOutFinancialDataBuilder
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
import com.fintechplatform.android.secure3d.Secure3DBuilder
import com.fintechplatform.android.transactions.TransactionsBuilder
import com.raizlabs.android.dbflow.config.FlowManager


/**
 * FintechPlatform Main Class.
 * Obtain all FintechPlatform objects using this class.
 * Notice: before use any factory method you have to call initialize.
 *
 * Usage Example:
 *
    val config = FintechPlatform.Configuration("DWAPLATFORM_SANDBOX_HOSTNAME", true)
    FintechPlatform.initialize(config)

    val cardAPI = FintechPlatform.getCardAPI(context)

    //get token from POST call:
    // rest/v1/:clientId/users/:userId/accounts/:accountId/cards
    val token = "XXXXXXXXYYYYZZZZKKKKWWWWWWWWWWWWTTTTTTTFFFFFFF...."
    val cardNumber = "1234567812345678"
    val expiration = "1122"
    val cxv = "123"

    cardAPI.registerCard(token, cardNumber, expiration, cxv) { card, e ->
        if (e != null) {
            Log.e("Sample", e.message)
            return@registerCard
        }
        card?.let {
            Log.d("Sample", "card id: $card.id")
        }
    }
 *
 */
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

        fun buildFinancialData(): PayInPayOutFinancialDataBuilder {
            return PayInPayOutFinancialDataBuilder()
        }


    }

}

