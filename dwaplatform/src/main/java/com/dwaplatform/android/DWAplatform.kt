package com.dwaplatform.android

import android.content.Context
import com.android.volley.toolbox.Volley
import com.dwaplatform.android.account.balance.BalanceBuilder
import com.dwaplatform.android.api.volley.VolleyRequestProvider
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider
import com.dwaplatform.android.card.CardAPI
import com.dwaplatform.android.card.api.CardRestAPI
import com.dwaplatform.android.card.helpers.CardHelper
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.card.helpers.SanityCheck
import com.dwaplatform.android.log.Log
import com.dwaplatform.android.payin.PayInBuilder
import com.raizlabs.android.dbflow.config.FlowConfig
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

    data class Configuration(val hostName: String, val sandbox: Boolean)

    companion object {
        @Volatile private var conf:  Configuration? = null
        @Volatile private var cardAPIInstance: CardAPI? = null
        //@Volatile public var payinInstance: PayIn? = null

        /**
         * Initialize DWAplatform
         * @param config Configuration
         */
        fun initialize(config: Configuration, context: Context) {
            conf = config
            FlowManager.init(context)
        }

        /**
         * Factory method to get CardAPI object
         */
        fun getCardAPI(context: Context): CardAPI =
                cardAPIInstance ?: synchronized(this) {

                    val c = conf ?: throw Exception("DWAplatform init configuration missing")

                    cardAPIInstance ?: buildCardAPI(c.hostName,
                            context,
                            c.sandbox).also {
                        cardAPIInstance = it }
                }

        private fun buildCardAPI(hostName: String, context: Context, sandbox: Boolean): CardAPI {

            return CardAPI(CardRestAPI(hostName,
                    VolleyRequestQueueProvider(Volley.newRequestQueue(context)),
                    VolleyRequestProvider(),
                    JSONHelper(),
                    sandbox), Log(), CardHelper(SanityCheck()))
        }

        fun buildPayIn() : PayInBuilder {
            return PayInBuilder()
        }

        fun buildBalance(): BalanceBuilder {
            return BalanceBuilder()
        }

        //fun getPayIn(account: Account): PayIn =

            //payinInstance ?: synchronized(this) {
          //      val c = conf ?: throw Exception("DWAplatform init configuration missing")

                //payinInstance ?: buildPayIn(account).also { payinInstance = it }
            //}

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

