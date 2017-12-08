package com.dwaplatform.android

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.dwaplatform.android.payin.models.PayInConfiguration
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

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

    /*@Test
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



    }*/


    @Test
    fun test_startPayInUI() {
        val context = InstrumentationRegistry.getTargetContext()

        val hostName = "https://api.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A"

        val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"
        val paymentCardId = "7e30a4d8-918b-11e7-931d-8b5fb14500e0"

        val payIn = DWAplatform.buildPayIn()
        val payInComponent = payIn.createPayInUIComponent(
                hostName,
                token, PayInConfiguration(userId, accountId, paymentCardId))
        payInComponent.getPayInUI().start(context)

        Thread.sleep(10000)



    }

}
