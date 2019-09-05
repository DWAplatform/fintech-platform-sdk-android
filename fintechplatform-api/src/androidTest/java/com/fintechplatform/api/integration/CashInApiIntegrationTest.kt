package com.fintechplatform.api.integration

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.ErrorCode
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.payin.models.PayInReply
import com.fintechplatform.api.payin.models.PayInStatus
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
class PayInApiIntegrationTest {
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

        hostName = "http://10.0.0.7:9000"
        accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MjY1NTAzOTYsImlhdCI6MTUyNjQ2Mzk5NiwidGVuYW50SWQiOiJiMDQ1NmNjNC01NTc0LTQ4M2UtYjRmOS1lODg2Y2MzZmVkZmUiLCJhY2NvdW50VHlwZSI6IlBFUlNPTkFMIiwib3duZXJJZCI6IjY0MzBlMmE2LTE1YmUtNDIzZS1hN2IwLWQ2NGQ2ODQ0NWNiNCIsImFjY291bnRJZCI6IjE2ZjA3NGRmLWU4YjYtNDk1NC1hZDVhLTE2MTEzM2I5YzQyOSIsImp3dFR5cGUiOiJBQ0NPVU5UIiwic2NvcGUiOlsiTElOS0VEX0NBUkQiLCJMSU5LRURfQ0FSRF9DQVNIX0lOIl19.tIFnXLfKgfw34UURRLiN_9DAAnyVfFFYws8StFZABMMfGQMTCtYe89h0w-7hszLhz954xWBEHpGxRo4-GWE9yw"
        tenantId = "b0456cc4-5574-483e-b4f9-e886cc3fedfe"
        userId = "6430e2a6-15be-423e-a7b0-d64d68445cb4"
        accountId = "16f074df-e8b6-4954-ad5a-161133b9c429"
    }

    @Test
    fun payIn() {
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

        val payInAPI = FintechPlatformAPI.getPayIn(hostName, context)


        // calcolo le Fee
        var payInFee : Money? = null
        var payInErrorFee : Exception? = null
        val expectationPayInFee = CountDownLatch(1)

        payInAPI.payInFee(accessToken, tenantId, accountId, userId, "PERSONAL", paymentCard!!.id!!, Money(1000L, "EUR")) { optPayInFee, optError ->
            payInFee = optPayInFee
            payInErrorFee = optError
            expectationPayInFee.countDown()
        }

        expectationPayInFee.await(600, TimeUnit.SECONDS)
        Assert.assertNotNull(payInFee)
        Assert.assertNull(payInErrorFee)
        Assert.assertEquals(0L, payInFee?.value)

        // calcolo payIn no needs 3D Secure
        var payIn1 : PayInReply? = null
        var payInError1 : Exception? = null
        val expectationPayIn1 = CountDownLatch(1)
        val idempotency10eur = UUID.randomUUID().toString()

        payInAPI.payIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(1000L, "EUR"), idempotency10eur) { optPayIn, optError ->
            payIn1 = optPayIn
            payInError1 = optError
            expectationPayIn1.countDown()
        }

        expectationPayIn1.await(600, TimeUnit.SECONDS)
        Assert.assertNull(payInError1)
        Assert.assertNotNull(payIn1)
        Assert.assertFalse(payIn1?.securecodeneeded!!)
        Assert.assertEquals(PayInStatus.SUCCEEDED, payIn1?.status)


        // calcolo payIn needs 3D Secure
        var payIn2 : PayInReply? = null
        var payInError2 : Exception? = null
        val expectationPayIn2 = CountDownLatch(1)
        val idempotency100eur = UUID.randomUUID().toString()

        payInAPI.payIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(10000L, "EUR"), idempotency100eur) { optPayIn, optError ->
            payIn2 = optPayIn
            payInError2 = optError
            expectationPayIn2.countDown()
        }

        expectationPayIn2.await(600, TimeUnit.SECONDS)
        Assert.assertNull(payInError2)
        Assert.assertNotNull(payIn2)
        Assert.assertTrue(payIn2?.securecodeneeded!!)
        Assert.assertEquals(PayInStatus.CREATED, payIn2?.status)

        // calcolo payIn Failed
        var payInFailed : PayInReply? = null
        var payInErrorFailed : Exception? = null
        val expectationPayInFailed = CountDownLatch(1)
        val idempotencyFailed = UUID.randomUUID().toString()

        payInAPI.payIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(-10L, "EUR"), idempotencyFailed) { optPayIn, optError ->
            payInFailed = optPayIn
            payInErrorFailed = optError
            expectationPayInFailed.countDown()
        }

        expectationPayInFailed.await(600, TimeUnit.SECONDS)

        Assert.assertNull(payInFailed)
        Assert.assertNotNull(payInErrorFailed)
        Assert.assertTrue(payInErrorFailed is NetHelper.APIResponseError)
        (payInErrorFailed as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.asp_generic_error)
            it.message?.let {
                print(it)
            }
        }


        // calcolo payIn Exception
        var payInExc : PayInReply? = null
        var payInErrorExc : Exception? = null
        val expectationPayInExc = CountDownLatch(1)
        val idempotencyExc = UUID.randomUUID().toString()

        payInAPI.payIn(accessToken, userId, UUID.randomUUID().toString(), "PERSONAL", tenantId, paymentCard!!.id!!, Money(100L, "EUR"), idempotencyExc) { optPayIn, optError ->
            payInExc = optPayIn
            payInErrorExc = optError
            expectationPayInExc.countDown()
        }

        expectationPayInExc.await(600, TimeUnit.SECONDS)

        Assert.assertNull(payInExc)
        Assert.assertNotNull(payInErrorExc)
        Assert.assertTrue(payInErrorExc is NetHelper.APIResponseError)
        (payInErrorExc as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.authentication_error)
        }
    }
}