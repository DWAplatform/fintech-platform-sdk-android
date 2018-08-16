package com.fintechplatform.api.card.api

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.card.models.PaymentCard
import com.fintechplatform.api.cashin.models.CashInResponse
import com.fintechplatform.api.money.Currency
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.ErrorCode
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.user.models.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class PaymentCardAPITest {
    lateinit var hostName: String
    lateinit var accessToken: String

    lateinit var account: Account

    @Before
    fun setUp() {

        hostName = "http://www.sandbox.lightbankingservices.com/api/v1"

        /**
         * go to api-test path:
         * fintech-platform-api-test user$
         *
         * and run this script to obtain ids and params:
         *
         * SDK_IOS_WORKSPACE_PATH="" ./node_modules/mocha/bin/mocha tests/mobile_sdk_test.js
         */

        val tenantId = "b0456cc4-5574-483e-b4f9-e886cc3fedfe"
        val userId = "b3483f59-c7f2-41aa-9d45-e21dbf4722ca"
        val accountId = "f3f931ca-9104-4a51-b74c-11370f358636"

        accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MzQ0OTcwNTgsImlhdCI6MTUzNDQxMDY1OCwidGVuYW50SWQiOiJiMDQ1NmNjNC01NTc0LTQ4M2UtYjRmOS1lODg2Y2MzZmVkZmUiLCJhY2NvdW50VHlwZSI6IlBFUlNPTkFMIiwib3duZXJJZCI6ImIzNDgzZjU5LWM3ZjItNDFhYS05ZDQ1LWUyMWRiZjQ3MjJjYSIsImFjY291bnRJZCI6ImYzZjkzMWNhLTkxMDQtNGE1MS1iNzRjLTExMzcwZjM1ODYzNiIsImp3dFR5cGUiOiJBQ0NPVU5UIiwic2NvcGUiOlsiTElOS0VEX0NBUkQiLCJMSU5LRURfQ0FSRF9DQVNIX0lOIl19.f6FWhNHp1ZNAtyRTtGypuGZEZPKLN0BnOHL2w_dmEbV1tORH9yo_OwJZ015LpfzHXyK_rnqfIl0gneFbFME0pg"

        account = Account.personalAccount(User(UUID.fromString(tenantId), UUID.fromString(userId)), UUID.fromString(accountId))

    }

    @Test
    fun testInactiveCards() {
        val context = InstrumentationRegistry.getTargetContext()
        val paymentCardAPI = FintechPlatformAPI.getPaymentCard(hostName, context, true)
        val cashInAPI = FintechPlatformAPI.getPayIn(hostName, context)

        // get initial payment card for account user
        // getPaymentCard NOT_ACTIVE, expect two Cards
        val expectationGetCardsN = CountDownLatch(1)
        var card: PaymentCard? = null
        var cardsListOptError: Exception? = null
        paymentCardAPI.registerCard(accessToken, account,"1234123412341234", "0122", "123", Currency.EUR) {
            optCard, optError ->
            cardsListOptError = optError
            card = optCard

            expectationGetCardsN.countDown()
        }

        expectationGetCardsN.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(card)

        // cashIn with invalid card
        var cashIn1 : CashInResponse? = null
        var cashInError1 : Exception? = null
        val expectationCashIn1 = CountDownLatch(1)
        val idempotency10eur = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, account, card!!.cardId!!, Money(33300000L, "EUR"), idempotency10eur) { optCashIn, optError ->
            cashIn1 = optCashIn
            cashInError1 = optError
            expectationCashIn1.countDown()
        }

        expectationCashIn1.await(600, TimeUnit.SECONDS)
        Assert.assertNotNull(cashInError1)
        Assert.assertNull(cashIn1)
        Assert.assertTrue(cashInError1 is NetHelper.APIResponseError)
        (cashInError1 as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.asp_transaction_amount_is_higher_than_maximum_permitted_amount)
        }

    }
}