package com.fintechplatform.api.integration

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.card.models.PaymentCard
import com.fintechplatform.api.cashin.models.CashInResponse
import com.fintechplatform.api.cashin.models.CashInStatus
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

/**
 * Created by ingrid on 08/05/18.
 */
@RunWith(AndroidJUnit4::class)
class CashInApiIntegrationTest {

    lateinit var hostName: String
    lateinit var accessToken: String
    lateinit var account: Account

    @Before
    fun setUp() {

        hostName = "http://api.sandbox.lightbankingservices.com"

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
    fun cashIn() {
        val context = InstrumentationRegistry.getTargetContext()

        val expectationFake = CountDownLatch(1)
        val expectationMillisecondsAwait = 10L

        // creo carta prima del cashin.
        val paymentCardAPI = FintechPlatformAPI.getPaymentCard(hostName, context, true)
        var paymentCard : PaymentCard? = null
        var paymentCardError: Exception? = null
        val expectationRegisterCard = CountDownLatch(1)
        paymentCardAPI.registerCard(accessToken,account,"1234123412341234",  "0122", "123", Currency.EUR) { optPaymentCard, optError ->
            paymentCard = optPaymentCard
            paymentCardError = optError
            expectationRegisterCard.countDown()
        }

        expectationRegisterCard.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(paymentCardError)
        Assert.assertNotNull(paymentCard)

        val cashInAPI = FintechPlatformAPI.getPayIn(hostName, context)

        // Fee
        var cashInFee : Money? = null
        var cashInErrorFee : Exception? = null
        val expectationCashInFee = CountDownLatch(1)

        cashInAPI.cashInFee(accessToken, account, paymentCard!!.cardId!!, Money(1000L, Currency.EUR)) { optCashInFee, optError ->
            cashInFee = optCashInFee
            cashInErrorFee = optError
            expectationCashInFee.countDown()
        }

        expectationCashInFee.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNotNull(cashInFee)
        Assert.assertNull(cashInErrorFee)
        Assert.assertEquals(0L, cashInFee?.value)

        // cashIn no needs 3D Secure
        var cashIn1 : CashInResponse? = null
        var cashInError1 : Exception? = null
        val expectationCashIn1 = CountDownLatch(1)
        val idempotency10eur = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, account, paymentCard!!.cardId!!, Money(1000L, Currency.EUR), idempotency10eur) { optCashIn, optError ->
            cashIn1 = optCashIn
            cashInError1 = optError
            expectationCashIn1.countDown()
        }

        expectationCashIn1.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(cashInError1)
        Assert.assertNotNull(cashIn1)
        Assert.assertFalse(cashIn1?.securecodeneeded!!)
        Assert.assertEquals(CashInStatus.SUCCEEDED, cashIn1?.status)


        // cashIn needs 3D Secure
        var cashIn2 : CashInResponse? = null
        var cashInError2 : Exception? = null
        val expectationCashIn2 = CountDownLatch(1)
        val idempotency100eur = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, account, paymentCard!!.cardId!!, Money(10000L, Currency.EUR), idempotency100eur) { optCashIn, optError ->
            cashIn2 = optCashIn
            cashInError2 = optError
            expectationCashIn2.countDown()
        }

        expectationCashIn2.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(cashInError2)
        Assert.assertNotNull(cashIn2)
        Assert.assertTrue(cashIn2?.securecodeneeded!!)
        Assert.assertEquals(CashInStatus.CREATED, cashIn2?.status)

        // calcolo cashIn generic error
        var cashInFailed : CashInResponse? = null
        var cashInErrorFailed : Exception? = null
        val expectationCashInFailed = CountDownLatch(1)
        val idempotencyFailed = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, account, paymentCard!!.cardId!!, Money(-10L, Currency.EUR), idempotencyFailed) { optCashIn, optError ->
            cashInFailed = optCashIn
            cashInErrorFailed = optError
            expectationCashInFailed.countDown()
        }

        expectationCashInFailed.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(cashInFailed)
        Assert.assertNotNull(cashInErrorFailed)
        Assert.assertTrue(cashInErrorFailed is NetHelper.APIResponseError)
        (cashInErrorFailed as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(ErrorCode.asp_generic_error, it.errors?.get(0)?.code)
            it.message?.let {
                print(it)
            }
        }

        // calcolo cashIn Failed
        var cashInFailed2 : CashInResponse? = null
        var cashInErrorFailed2: Exception? = null
        val expectationCashInFailed2 = CountDownLatch(1)
        val idempotencyFailed2 = UUID.randomUUID().toString()

        cashInAPI.cashIn(accessToken, account, paymentCard!!.cardId!!, Money(0L, Currency.EUR), idempotencyFailed2) { optCashIn, optError ->
            cashInFailed2 = optCashIn
            cashInErrorFailed2 = optError
            expectationCashInFailed2.countDown()
        }

        expectationCashInFailed2.await(10, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(cashInFailed2)
        Assert.assertNotNull(cashInErrorFailed2)
        Assert.assertTrue(cashInErrorFailed2 is NetHelper.APIResponseError)
        (cashInErrorFailed2 as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(ErrorCode.asp_creditedfunds_must_be_more_than_0, it.errors?.get(0)?.code)
            it.message?.let {
                print(it)
            }
        }

        // cashIn Exception
        var cashInExc : CashInResponse? = null
        var cashInErrorExc : Exception? = null
        val expectationCashInExc = CountDownLatch(1)
        val idempotencyExc = UUID.randomUUID().toString()

        account = Account.personalAccount(User(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID())
        cashInAPI.cashIn(accessToken, account, paymentCard!!.cardId!!, Money(100L, Currency.EUR), idempotencyExc) { optCashIn, optError ->
            cashInExc = optCashIn
            cashInErrorExc = optError
            expectationCashInExc.countDown()
        }

        expectationCashInExc.await(600, TimeUnit.SECONDS)
        expectationFake.await(expectationMillisecondsAwait, TimeUnit.MILLISECONDS)

        Assert.assertNull(cashInExc)
        Assert.assertNotNull(cashInErrorExc)

        Assert.assertTrue(cashInErrorExc is NetHelper.APIResponseError)
        (cashInErrorExc as NetHelper.APIResponseError?)?.let {
            Assert.assertEquals(ErrorCode.authentication_error, it.errors?.get(0)?.code)
        }
    }
}