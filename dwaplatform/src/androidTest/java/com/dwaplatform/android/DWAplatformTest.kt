package com.dwaplatform.android

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.dwaplatform.android.DWAplatform
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.ui.PayInPresenterFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class DWAplatformTest {


    @Test
    fun test_getCardAPI() {

        val context = InstrumentationRegistry.getTargetContext()

        val conf = DWAplatform.Configuration("http://localhost", true)
        DWAplatform.initialize(conf)
        val cardAPI = DWAplatform.getCardAPI(context)
        Assert.assertNotNull(cardAPI)
        Assert.assertNotNull(cardAPI.cardHelper)
        Assert.assertNotNull(cardAPI.log)
        Assert.assertNotNull(cardAPI.restAPI)
        Assert.assertEquals("http://localhost", cardAPI.restAPI.hostName)
        Assert.assertEquals(true, cardAPI.restAPI.sandbox)
        Assert.assertNotNull(cardAPI.restAPI.jsonHelper)
        Assert.assertNotNull(cardAPI.restAPI.queue)
        Assert.assertNotNull(cardAPI.restAPI.requestProvider)
    }

    @Test
    fun test_getPayIn() {
        val context = InstrumentationRegistry.getTargetContext()

        val user = DWAplatform.buildUser()
        val account = DWAplatform.buildAccount(user)
        val balance = DWAplatform.buildBalance(account)
        val paymentCard = DWAplatform.buildPaymentCard(account)
        val payIn = DWAplatform.buildPayIn(account, balance, paymentCard)



        val ui = payIn.buildPayInUI(payIn.buildAPI("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A",
                "https://api.sandbox.dwaplatform.com", context), MoneyHelper(), FeeHelper(), PayInPresenterFactory())


        ui.start(context)
        Thread.sleep(10000)



    }

}
