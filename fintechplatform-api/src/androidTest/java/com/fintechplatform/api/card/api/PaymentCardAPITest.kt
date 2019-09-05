package com.fintechplatform.api.card.api

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.ErrorCode
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.payin.models.PayInReply
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
    lateinit var tenantId: String
    lateinit var userId: String
    lateinit var accountId: String

    @Before
    fun setUp() {
        /*hostName = ProcessInfo.processInfo.environment["HOSTNAME"]!
        accessToken = ProcessInfo.processInfo.environment["ACCOUNT_TOKEN"]!
        tenantId = ProcessInfo.processInfo.environment["TENANT_ID"]!
        userId = ProcessInfo.processInfo.environment["OWNER_ID"]!
        accountId = ProcessInfo.processInfo.environment["ACCOUNT_ID"]!*/

        hostName = ""
        accessToken = ""
        tenantId = ""
        userId = ""
        accountId = ""

    }

    @Test
    fun testInactiveCards() {
        val context = InstrumentationRegistry.getTargetContext()
        val paymentCardAPI = FintechPlatformAPI.getPaymentCard(hostName, context, true)
        val payInAPI = FintechPlatformAPI.getPayIn(hostName, context)

        // get initial payment card for account user
        // getPaymentCard NOT_ACTIVE, expect two Cards
        val expectationGetCardsN = CountDownLatch(1)
        var card: PaymentCardItem? = null
        var cardsListOptError: Exception? = null
        paymentCardAPI.registerCard(accessToken, userId, accountId, "PERSONAL", tenantId,"1234123412341234", "0122", "123", "EUR") {
            optCard, optError ->
            cardsListOptError = optError
            card = optCard

            expectationGetCardsN.countDown()
        }

        expectationGetCardsN.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(card)

        // payIn with invalid card
        var payIn1 : PayInReply? = null
        var payInError1 : Exception? = null
        val expectationPayIn1 = CountDownLatch(1)
        val idempotency10eur = UUID.randomUUID().toString()

        payInAPI.payIn(accessToken, userId, accountId, "PERSONAL", tenantId, card?.id!!, Money(33300000L, "EUR"), idempotency10eur) { optPayIn, optError ->
            payIn1 = optPayIn
            payInError1 = optError
            expectationPayIn1.countDown()
        }

        expectationPayIn1.await(600, TimeUnit.SECONDS)
        Assert.assertNotNull(payInError1)
        Assert.assertNull(payIn1)
        Assert.assertTrue(payInError1 is NetHelper.APIResponseError)
        (payInError1 as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.asp_transaction_amount_is_higher_than_maximum_permitted_amount)
        }

    }
}