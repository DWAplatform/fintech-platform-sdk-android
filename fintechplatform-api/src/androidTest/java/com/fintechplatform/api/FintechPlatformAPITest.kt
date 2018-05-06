package com.fintechplatform.api

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FintechPlatformAPITest {


    @Test
    fun test_getCardAPI() {

        val context = InstrumentationRegistry.getTargetContext()

//        val conf = FintechPlatformAPI.Configuration("http://localhost", true)
//        FintechPlatformAPI.initialize(conf, context)
//        val cardAPI = FintechPlatformAPI.getCardAPI(context)
//        Assert.assertNotNull(cardAPI)
//        Assert.assertNotNull(cardAPI.paymentCardHelper)
//        Assert.assertNotNull(cardAPI.log)
//        Assert.assertNotNull(cardAPI.restAPI)
//        Assert.assertEquals("http://localhost", cardAPI.restAPI.hostName)
//        Assert.assertEquals(true, cardAPI.restAPI.sandbox)
//        Assert.assertNotNull(cardAPI.restAPI.jsonHelper)
//        Assert.assertNotNull(cardAPI.restAPI.queue)
//        Assert.assertNotNull(cardAPI.restAPI.requestProvider)
    }

    /*@Test
    fun test_getPayIn() {
        val context = InstrumentationRegistry.getTargetContext()

        val user = FintechPlatformAPI.buildUser()
        val account = FintechPlatformAPI.buildAccount(user)
        val balance = FintechPlatformAPI.buildBalance(account)
        val paymentCard = FintechPlatformAPI.buildPaymentCard(account)
        val cashIn = FintechPlatformAPI.buildPayIn(account, balance, paymentCard)



        val ui = cashIn.buildPayInUI(cashIn.buildAPI("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A",
                "https://api.sandbox.dwaplatform.com", context), MoneyHelper(), FeeHelper(), PayInPresenterFactory())


        ui.start(context)
        Thread.sleep(10000)



    }*/


    @Test
    fun test_startPayInUI() {
        /*val context = InstrumentationRegistry.getTargetContext()

        val hostName = "https://restApi.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A"

        val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"
        val paymentCardId = "7e30a4d8-918b-11e7-931d-8b5fb14500e0"

        val payIn = FintechPlatformAPI.buildPayIn()
        val payInComponent = payIn.createPayInUIComponent(
                hostName,
                true,
                DataAccount(userId, accountId, paymentCardId))
        payInComponent.payInUI.start(context)

        Thread.sleep(20000)*/



    }

    @Test
    fun test_startSecure3dUI() {
        /*val context = InstrumentationRegistry.getTargetContext()

        val hostName = "https://restApi.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A"

        val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"
        val paymentCardId = "7e30a4d8-918b-11e7-931d-8b5fb14500e0"

        val payIn = FintechPlatformAPI.build3DSecure()
        val payInComponent = payIn.buildSecure3DUIComponent().secure3DUI.start(context, "www.google.com")

        Thread.sleep(20000)*/

    }

    /*
    @Test
    fun test_balanceHelper() {
        val context = InstrumentationRegistry.getTargetContext()

        val hostName = "https://api.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A"

        val ownerId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"

        val balanceBuilder = FintechPlatformAPI.buildBalance()
        val balanceHelperComponent = balanceBuilder.createBalanceHelperComponent(hostName, token, context)

        balanceHelperComponent.balanceHelper.getAndUpdateCachedBalance(ownerId, accountId) { optMoney, optError ->

            //
            Assert.assertNull(optError)
            Assert.assertNotNull(optMoney)



        }

        Thread.sleep(10000)
    }*/

}
