package com.dwaplatform.android

import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwaplatform.android.account.balance.BalanceBuilder
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.api.PaymentCardAPI
import com.dwaplatform.android.card.api.PaymentCardRestAPI
import com.dwaplatform.android.card.helpers.PaymentCardHelper
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.card.helpers.SanityCheck
import com.dwaplatform.android.db.PlatformDB
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.payin.PayInBuilder
import com.dwaplatform.android.secure3d.Secure3DBuilder
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
        @Volatile private var cardAPIInstance: PaymentCardAPI? = null
        //@Volatile public var payinInstance: PayIn? = null

        /**
         * Initialize DWAplatform
         * @param config Configuration
         */
        fun initialize(context: Context) {
            FlowManager.init(context)
            PlatformDB.init()
        }

        /**
         * Factory method to get PaymentCardAPI object
         */
        fun getCardAPI(hostName: String, sandbox: Boolean, context: Context): PaymentCardAPI =
                cardAPIInstance ?: synchronized(this) {
                    cardAPIInstance ?: buildCardAPI(hostName,
                            context,
                            sandbox).also {
                        cardAPIInstance = it }
                }

        private fun buildCardAPI(hostName: String, context: Context, sandbox: Boolean): PaymentCardAPI {

            return PaymentCardAPI(PaymentCardRestAPI(hostName,
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

/*
        fun buildAccount(user: User) : Account {
            return Account(user)
        }

        fun buildUser(): User {
            return User()
        }

        fun buildPaymentCard(account: Account): PaymentCard {
            return PaymentCard(account)
        }

        fun buildBalance(account: Account): Balance {
            return Balance(account)
        }

*/



    }

}

