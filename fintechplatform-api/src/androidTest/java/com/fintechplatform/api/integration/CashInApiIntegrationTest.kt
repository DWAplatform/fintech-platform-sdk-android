package com.fintechplatform.api.integration

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.cashin.models.CashInReply
import com.fintechplatform.api.cashin.models.CashInStatus
import com.fintechplatform.api.money.Money
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by ingrid on 08/05/18.
 */
@RunWith(AndroidJUnit4::class)
class CashInApiIntegrationTest {
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
        accountId = ProcessInfo.processInfo.environment["ACCOUNT_ID"]!
        */

        hostName = "http://192.168.1.73:9000"
        accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MjU5MzcxNTgsImlhdCI6MTUyNTg1MDc1OCwidGVuYW50SWQiOiJiMDQ1NmNjNC01NTc0LTQ4M2UtYjRmOS1lODg2Y2MzZmVkZmUiLCJhY2NvdW50VHlwZSI6IlBFUlNPTkFMIiwib3duZXJJZCI6ImQ0NTk5MWU2LWIzYTItNDVlYy04YjFiLWZlM2Q2OTVhMjQ3YiIsImFjY291bnRJZCI6IjdiNWRlODNjLTUzN2ItNGQwNy1iYWVhLTk2NDc3ZmU2ZTIxZSIsImp3dFR5cGUiOiJBQ0NPVU5UIiwic2NvcGUiOlsiTElOS0VEX0NBUkQiLCJMSU5LRURfQ0FSRF9DQVNIX0lOIl19.h4sZmqX8W-zO36wFYNSzqd8H4D19my20s5qvjDka48JSdtrF7j8CarqgbKxrZSPLx3ict0Xa1lBB0xVwL86g0g"
        tenantId = "b0456cc4-5574-483e-b4f9-e886cc3fedfe"
        userId = "d45991e6-b3a2-45ec-8b1b-fe3d695a247b"
        accountId = "7b5de83c-537b-4d07-baea-96477fe6e21e"
    }

    @Test
    fun cashIn() {
        val context = InstrumentationRegistry.getTargetContext()

        // creo carta prima del cashin.
        val paymentCardAPI = FintechPlatformAPI.getPaymentCard(hostName, context, true)
        var paymentCard : PaymentCardItem? = null
        var paymentCardError: Exception? = null
        val expectationRegisterCard = CountDownLatch(1)
        paymentCardAPI.registerCard(accessToken, userId, accountId, "PERSONAL", tenantId, "1234123412341234",  "0122", "123", "EUR") { optPaymentCard, optError ->
            paymentCard = optPaymentCard
            paymentCardError = optError
            expectationRegisterCard.countDown()
        }

        expectationRegisterCard.await(600, TimeUnit.SECONDS)

        Assert.assertNull(paymentCardError)
        Assert.assertNotNull(paymentCard)

        val cashInAPI = FintechPlatformAPI.getPayIn(hostName, context)


        // calcolo le Fee
        var cashInFee : Money? = null
        var cashInErrorFee : Exception? = null
        val expectationCashInFee = CountDownLatch(1)

        cashInAPI.cashInFee(accessToken, tenantId, accountId, userId, "PERSONAL", paymentCard!!.id!!, Money(1000L, "EUR")) { optCashInFee, optError ->
            cashInFee = optCashInFee
            cashInErrorFee = optError
            expectationCashInFee.countDown()
        }

        expectationCashInFee.await(600, TimeUnit.SECONDS)
        Assert.assertNotNull(cashInFee)
        Assert.assertNull(cashInErrorFee)
        Assert.assertEquals(0L, cashInFee?.value)

        // calcolo cashIn no needs 3D Secure
        var cashIn1 : CashInReply? = null
        var cashInError1 : Exception? = null
        val expectationCashIn1 = CountDownLatch(1)
        val idempotency10eur = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(1000L, "EUR"), idempotency10eur) { optCashIn, optError ->
            cashIn1 = optCashIn
            cashInError1 = optError
            expectationCashIn1.countDown()
        }

        expectationCashIn1.await(600, TimeUnit.SECONDS)
        Assert.assertNull(cashInError1)
        Assert.assertNotNull(cashIn1)
        Assert.assertFalse(cashIn1?.securecodeneeded!!)
        Assert.assertEquals(CashInStatus.SUCCEEDED, cashIn1?.status)


        // calcolo cashIn needs 3D Secure
        var cashIn2 : CashInReply? = null
        var cashInError2 : Exception? = null
        val expectationCashIn2 = CountDownLatch(1)
        val idempotency100eur = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(10000L, "EUR"), idempotency100eur) { optCashIn, optError ->
            cashIn2 = optCashIn
            cashInError2 = optError
            expectationCashIn2.countDown()
        }

        expectationCashIn2.await(600, TimeUnit.SECONDS)
        Assert.assertNull(cashInError2)
        Assert.assertNotNull(cashIn2)
        Assert.assertTrue(cashIn2?.securecodeneeded!!)
        Assert.assertEquals(CashInStatus.CREATED, cashIn2?.status)
    }
}