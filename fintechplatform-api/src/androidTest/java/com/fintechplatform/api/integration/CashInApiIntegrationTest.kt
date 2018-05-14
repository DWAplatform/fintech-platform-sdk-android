package com.fintechplatform.api.integration

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.cashin.models.CashInReply
import com.fintechplatform.api.cashin.models.CashInStatus
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.ErrorCode
import com.fintechplatform.api.net.NetHelper
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
        accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MjYzNzA3MzYsImlhdCI6MTUyNjI4NDMzNiwidGVuYW50SWQiOiJiMDQ1NmNjNC01NTc0LTQ4M2UtYjRmOS1lODg2Y2MzZmVkZmUiLCJhY2NvdW50VHlwZSI6IlBFUlNPTkFMIiwib3duZXJJZCI6IjQyN2M5ODQwLWIxMTktNDJjYS04MGYxLTNhM2E4YmY2ODQyMSIsImFjY291bnRJZCI6ImMzZmQxOWViLTY3ZDQtNDI1MS1hZWZkLTRkNDc0NTA5MzM3ZSIsImp3dFR5cGUiOiJBQ0NPVU5UIiwic2NvcGUiOlsiTElOS0VEX0NBUkQiLCJMSU5LRURfQ0FSRF9DQVNIX0lOIl19.Xp-HEM46llMdrEfZUHAtjoJ_ffdeJm1WLMsF5_YWLvQLXo3KU0JW0EBwEZvwaSpNJVrzGhu86EYKmPJn4o1MPQ"
        tenantId = "b0456cc4-5574-483e-b4f9-e886cc3fedfe"
        userId = "427c9840-b119-42ca-80f1-3a3a8bf68421"
        accountId = "c3fd19eb-67d4-4251-aefd-4d474509337e"
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

        // calcolo cashIn Failed
        var cashInFailed : CashInReply? = null
        var cashInErrorFailed : Exception? = null
        val expectationCashInFailed = CountDownLatch(1)
        val idempotencyFailed = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, userId, accountId, "PERSONAL", tenantId, paymentCard!!.id!!, Money(-10L, "EUR"), idempotencyFailed) { optCashIn, optError ->
            cashInFailed = optCashIn
            cashInErrorFailed = optError
            expectationCashInFailed.countDown()
        }

        expectationCashInFailed.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cashInFailed)
        Assert.assertNotNull(cashInErrorFailed)
        Assert.assertTrue(cashInErrorFailed is NetHelper.APIResponseError)
        (cashInErrorFailed as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.asp_generic_error)
            it.message?.let {
                print(it)
            }
        }


        // calcolo cashIn Exception
        var cashInExc : CashInReply? = null
        var cashInErrorExc : Exception? = null
        val expectationCashInExc = CountDownLatch(1)
        val idempotencyExc = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, userId, UUID.randomUUID().toString(), "PERSONAL", tenantId, paymentCard!!.id!!, Money(100L, "EUR"), idempotencyExc) { optCashIn, optError ->
            cashInExc = optCashIn
            cashInErrorExc = optError
            expectationCashInExc.countDown()
        }

        expectationCashInExc.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cashInExc)
        Assert.assertNotNull(cashInErrorExc)
        Assert.assertTrue(cashInErrorExc is NetHelper.APIResponseError)
        (cashInErrorExc as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(it.errors?.get(0)?.code, ErrorCode.authentication_error)
        }
    }
}