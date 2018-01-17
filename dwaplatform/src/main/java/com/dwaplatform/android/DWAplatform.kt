package com.dwaplatform.android

import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwaplatform.android.account.balance.BalanceBuilder
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.PaymentCardBuilder
import com.dwaplatform.android.card.client.api.ClientCardAPI
import com.dwaplatform.android.card.client.api.ClientCardRestAPI
import com.dwaplatform.android.card.helpers.PaymentCardHelper
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.card.helpers.SanityCheck
import com.dwaplatform.android.db.PlatformDB
import com.dwaplatform.android.iban.IBANBuilder
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.payin.PayInBuilder
import com.dwaplatform.android.payout.PayOutBuilder
import com.dwaplatform.android.profile.ProfileBuilder
import com.dwaplatform.android.secure3d.Secure3DBuilder
import com.dwaplatform.android.transactions.TransactionsBuilder
import com.raizlabs.android.dbflow.config.FlowManager


/**
 * DWAplatform Main Class.
 * Obtain all DWAplatform objects using this class.
 * Notice: before use any factory method you have to call initialize.
 *
 * Usage Example:
 *
    val config = DWAplatform.Configuration("DWAPLATFORM_SANDBOX_HOSTNAME", true)
    DWAplatform.initialize(config)

    val cardAPI = DWAplatform.getCardAPI(context)

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
class DWAplatform {

    companion object {
        @Volatile private var cardAPIInstance: ClientCardAPI? = null
        //@Volatile public var payinInstance: PayIn? = null

        /**
         * Initialize DWAplatform
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

//        fun buildAuth(): AuthBuilder {
//            return AuthBuilder()
//        }

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

    }

}

