package com.fintechplatform.api.integration

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.card.helpers.DateTimeConversion
import com.fintechplatform.api.card.models.PaymentCardItem
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Created by tcappellari on 06/05/2018.
 */
@RunWith(AndroidJUnit4::class)
class PaymentCardApiIntegrationTest {

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

        hostName = "http://172.16.21.28:9000"
        accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MjU3NzU1NjEsImlhdCI6MTUyNTY4OTE2MSwidGVuYW50SWQiOiJiMDQ1NmNjNC01NTc0LTQ4M2UtYjRmOS1lODg2Y2MzZmVkZmUiLCJhY2NvdW50VHlwZSI6IlBFUlNPTkFMIiwib3duZXJJZCI6IjU5YzdhZDY4LTk2ZDQtNGEwNS04MGFmLWM2YjkzODdmNjYzMCIsImFjY291bnRJZCI6ImFiMTk2YWNjLTk1M2MtNDgzYS1hMGIxLTFmOGI2NDA3ZDRlZCIsImp3dFR5cGUiOiJBQ0NPVU5UIiwic2NvcGUiOlsiTElOS0VEX0NBUkQiLCJMSU5LRURfQ0FSRF9DQVNIX0lOIl19.vQ5v1jYmjpNUMMV_i8QhAERQQtszEltE-vvkdLR7TBiePbRfpXqN-fST3j-jISUQCr8fQzfg2Q3ejjn3S2cAXQ"
        tenantId = "b0456cc4-5574-483e-b4f9-e886cc3fedfe"
        userId = "59c7ad68-96d4-4a05-80af-c6b9387f6630"
        accountId = "ab196acc-953c-483a-a0b1-1f8b6407d4ed"
    }

    @Test
    fun test_convertFromAndToRFC3339() {
        val dateStr = "2018-05-07T13:54:55+02:00"
        // When
        val result = DateTimeConversion.convertFromRFC3339(dateStr)

        val resultString = DateTimeConversion.convert2RFC3339(result!!)

        // Then
        Assert.assertEquals(resultString, dateStr)
    }

    @Test
    fun testCards() {

        val context = InstrumentationRegistry.getTargetContext()
        val paymentCardAPI = FintechPlatformAPI.getPaymentCard(hostName, context, true)

        var paymentCard1: PaymentCardItem? = null
        var paymentCard1OptError: Exception? = null
        val expectationRegisterCard1 = CountDownLatch(1)

        paymentCardAPI.registerCard(accessToken, userId, accountId,  "PERSONAL", tenantId,
                "1234123412341234", "0122", "123", "EUR") {
            optPaymentCardItem, optError ->

            paymentCard1OptError = optError
            paymentCard1 = optPaymentCardItem

            expectationRegisterCard1.countDown()


        }

        expectationRegisterCard1.await(600, TimeUnit.SECONDS)
        
        Assert.assertNull(paymentCard1OptError)
        Assert.assertNotNull(paymentCard1)

        
        // getPaymentCards, expect only the First Card created
        val expectationGetCards1 = CountDownLatch(1)
        var cardsList: List<PaymentCardItem>? = null
        var cardsListOptError: Exception? = null
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId)
        { optList, optError ->

            cardsListOptError = optError
            cardsList = optList

            expectationGetCards1.countDown()
        }

        expectationGetCards1.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(cardsList)

//        Assert.assertEquals(cardsList!!.size, 1)
        Assert.assertTrue(cardsList!!.contains(paymentCard1!!))

        
        // create Second Card
        var paymentCard2: PaymentCardItem?= null
        var paymentCard2OptError: Exception?= null
        val expectationRegisterCard2 = CountDownLatch(1)
        paymentCardAPI.registerCard(accessToken, userId, accountId,  "PERSONAL", tenantId,
                "9876987698769876", "1224", "987", 
                "EUR") { 
            optPaymentCardItem, optError ->

            paymentCard2OptError = optError
            paymentCard2 = optPaymentCardItem
            expectationRegisterCard2.countDown()
        }

        expectationRegisterCard2.await(600, TimeUnit.SECONDS)

        Assert.assertNull(paymentCard2OptError)
        Assert.assertNotNull(paymentCard2)

        // getPaymentCards, expect two Cards
        val expectationGetCards2 = CountDownLatch(1)
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId) {
            optList, optError ->
            cardsListOptError = optError
            cardsList = optList

            expectationGetCards2.countDown()
        }

        expectationGetCards2.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(cardsList)
//        Assert.assertEquals(cardsList!!.size, 2)

        Assert.assertTrue(cardsList!!.contains(paymentCard1!!))
        Assert.assertTrue(cardsList!!.contains(paymentCard2!!))


        // set the First Card as Default
        val expectationDefaultCard1 = CountDownLatch(1)
        var setDefaultCard1:PaymentCardItem?= null
        var setDefaultCard1optError:Exception?= null
        paymentCard1?.id.let { cardId ->
            paymentCardAPI.setDefaultPaymentCard(accessToken, userId, accountId, "PERSONAL", tenantId, cardId!!) {
                optPaymentCard, optError ->

                setDefaultCard1 = optPaymentCard
                setDefaultCard1optError = optError
                expectationDefaultCard1.countDown()
            }
        }

        expectationDefaultCard1.await(600, TimeUnit.SECONDS)

        Assert.assertNull(setDefaultCard1optError)
        Assert.assertNotNull(setDefaultCard1)

        val paymentCard1Default = PaymentCardItem(paymentCard1!!.id,
                paymentCard1!!.accountId,
                paymentCard1!!.alias,
                paymentCard1!!.expiration,
                paymentCard1!!.currency,
                true,
                paymentCard1!!.status,
                paymentCard1!!.issuer,
                paymentCard1!!.created,
                setDefaultCard1!!.updated)

        Assert.assertEquals(paymentCard1Default, setDefaultCard1)

        Assert.assertTrue(setDefaultCard1!!.updated!!.after(paymentCard1!!.updated!!))


        // getPaymentCard, expect 2 cards, the first as default and the other not
        val expectationGetCards3 = CountDownLatch(1)
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId)
        { optList, optError ->

            cardsListOptError = optError
            cardsList = optList

            expectationGetCards3.countDown()
        }

        expectationGetCards3.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(cardsList)
        Assert.assertEquals(cardsList!!.size, 2)

        Assert.assertTrue(cardsList!!.contains(paymentCard1Default))

        val paymentCard2NotDefault = PaymentCardItem(paymentCard2!!.id,
                paymentCard2!!.accountId,
                paymentCard2!!.alias,
        paymentCard2!!.expiration,
                paymentCard2!!.currency,
                false,
                paymentCard2!!.status,
        paymentCard2!!.issuer, paymentCard2!!.created, setDefaultCard1!!.updated)
        Assert.assertTrue(cardsList!!.contains(paymentCard2NotDefault))


        // set the Second Card as Default
        var setDefaultCard2:PaymentCardItem?= null
        var setDefaultCard2optError:Exception?= null
        val expectationDefaultCard2 = CountDownLatch(1)
        paymentCard2?.id.let { cardId ->
            paymentCardAPI.setDefaultPaymentCard(accessToken, userId, accountId,
                    "PERSONAL", tenantId, cardId!!) {
            optPaymentCard, optError ->
                setDefaultCard2 = optPaymentCard
                setDefaultCard2optError = optError

                expectationDefaultCard2.countDown()
            }
        }
        expectationDefaultCard2.await(600, TimeUnit.SECONDS)

        Assert.assertNull(setDefaultCard2optError)
        Assert.assertNotNull(setDefaultCard2)

        val paymentCard2Default = PaymentCardItem(
                paymentCard2!!.id,
                paymentCard2!!.accountId,
                paymentCard2!!.alias,
        paymentCard2!!.expiration,
                paymentCard2!!.currency,
                true,
        paymentCard2!!.status,
         paymentCard2!!.issuer,
                paymentCard2!!.created, setDefaultCard2!!.updated)

        Assert.assertEquals(paymentCard2Default, setDefaultCard2)


        // getPaymentCard, expect 2 cards, the second as default and the other not
        val expectationGetCards4 = CountDownLatch(1)
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId) {
            optList, optError ->

            cardsListOptError = optError
            cardsList = optList

            expectationGetCards4.countDown()
        }

        expectationGetCards4.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(cardsList)

        Assert.assertEquals(cardsList!!.size, 2)

        val paymentCard1NotDefault = PaymentCardItem(
                paymentCard1!!.id,
                paymentCard1!!.accountId,
        paymentCard1!!.alias,
        paymentCard1!!.expiration,
                paymentCard1!!.currency,
                false,
                paymentCard1!!.status,
        paymentCard1!!.issuer,
        paymentCard1!!.created,
        setDefaultCard2!!.updated)

        Assert.assertTrue(cardsList!!.contains(paymentCard1NotDefault))
        Assert.assertTrue(cardsList!!.contains(paymentCard2Default))

        // delete the second Card
        val expectationDeleteCard1 = CountDownLatch(1)
        var deleteCard1OptError: Exception?= null
        //var deleteCard1: Boolean?= null
        paymentCard2?.id.let { cardId ->
            paymentCardAPI.deletePaymentCard(accessToken, userId, accountId,  "PERSONAL", tenantId,
                    cardId!!) { optError ->
                deleteCard1OptError = optError
                //deleteCard1 = true
                expectationDeleteCard1.countDown()
            }
        }

        expectationDeleteCard1.await(600, TimeUnit.SECONDS)

        Assert.assertNull(deleteCard1OptError)
        //Assert.assertNotNull(deleteCard1, "Doesn't delete payment card")
        //Assert.assertTrue(deleteCard1!)

        // getPaymentCard, expect only the first card
        val expectationGetCards5 = CountDownLatch(1)
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId) {
            optList, optError ->

            cardsListOptError = optError
            cardsList = optList

            expectationGetCards5.countDown()
        }

        expectationGetCards5.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNotNull(cardsList)

        Assert.assertEquals(cardsList?.size, 1)
        Assert.assertTrue(cardsList!!.contains(paymentCard1NotDefault))

        // deleteCard the first card
        val expectationDeleteCard2 = CountDownLatch(1)
        var deleteCard2OptError: Exception?= null
        //var deleteCard2:Bool?= null
        paymentCard1?.id.let { cardId ->
            paymentCardAPI.deletePaymentCard(accessToken,
                    userId, accountId,  "PERSONAL", tenantId, cardId!!) {
                optError ->

                deleteCard2OptError = optError
                //deleteCard2 = optBool

                expectationDeleteCard2.countDown()
            }
        }
        expectationDeleteCard2.await(600, TimeUnit.SECONDS)

        Assert.assertNull(deleteCard2OptError)
        //Assert.assertNull(deleteCard2, "Doesn't delete payment card")
        //Assert.assertTrue(deleteCard2!)

        // getPaymentCard, expect empty cards
        val expectationGetCards6 = CountDownLatch(1)
        paymentCardAPI.getPaymentCards(accessToken, userId, accountId,  "PERSONAL", tenantId) {
            optList, optError ->

            cardsListOptError = optError
            cardsList = optList

            expectationGetCards6.countDown()
        }

        expectationGetCards6.await(600, TimeUnit.SECONDS)

        Assert.assertNull(cardsListOptError)
        Assert.assertNull(cardsList)
        Assert.assertEquals(cardsList?.size, 0)



    }


}