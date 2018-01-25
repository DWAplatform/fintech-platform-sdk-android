package com.fintechplatform.android.card

import com.fintechplatform.android.card.client.api.ClientCardAPI
import com.fintechplatform.android.card.client.api.ClientCardRestAPI
import com.fintechplatform.android.card.helpers.PaymentCardHelper
import com.fintechplatform.android.card.helpers.SanityCheckException
import com.fintechplatform.android.card.helpers.SanityItem
import com.fintechplatform.android.card.models.PaymentCardItem
import com.fintechplatform.android.log.Log
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.util.*

class PaymentPaymentCardAPITest {

    @Mock lateinit var log: Log
    @Mock lateinit var paymentCardHelper: PaymentCardHelper
    @Mock lateinit var restAPI: ClientCardRestAPI

    val cardNumber = "1234123412341234"
    val expiration = "1122"
    val cxv = "123"

    lateinit var paymentCardAPI: ClientCardAPI

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        paymentCardAPI = ClientCardAPI(restAPI, log, paymentCardHelper)
    }

    @Test
    fun registerCard_CardWrongFormat() {
        // Given
        val exception = SanityCheckException(SanityItem("field", "value", "regExp"))
        Mockito.`when`(paymentCardHelper.checkCardFormat(cardNumber, expiration, cxv))
                .thenThrow(exception)

        try {
            // When
            paymentCardAPI.registerCard("token", cardNumber, expiration, cxv) { optCard, optException -> }

            // Then
            Assert.fail("Expected throw PaymentCard Format Sanity Check exception")
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertEquals(exception, e)
        }
    }

    @Test
    fun registerCard_postCardRegisterFailed() {
        // Given
        Mockito.`when`(paymentCardHelper.generateAlias(cardNumber)).thenReturn("1234XXXXXXXX1234")
        val e = Exception()

        var handlerCalled = 0
        // When
        paymentCardAPI.registerCard("1111-1111", cardNumber, expiration, cxv) { optCard, optException ->
            // Then
            handlerCalled++
            Assert.assertNull(optCard)
            Assert.assertNotNull(optException)
            Assert.assertEquals(e, optException!!)
        }

        // Then
        val captorToken = argumentCaptor<String>()
        val captorAlias = argumentCaptor<String>()
        val captorExpiration = argumentCaptor<String>()
        val captorHandler = argumentCaptor<(ClientCardRestAPI.CardRegistration?, Exception?) -> Unit>()
        verify(restAPI).postCardRegister(captorToken.capture(), captorAlias.capture(),
                captorExpiration.capture(), captorHandler.capture())

        Assert.assertEquals("1111-1111", captorToken.lastValue)
        Assert.assertEquals("1234XXXXXXXX1234", captorAlias.lastValue)
        Assert.assertEquals(expiration, captorExpiration.lastValue)
        Assert.assertNotNull(captorHandler.lastValue)

        captorHandler.lastValue.invoke(null, e)
        Assert.assertEquals(1, handlerCalled)

    }

    @Test
    fun registerCard_postCardRegisterSuccess_getCardSafeCalledFailed() {
        // Given
        Mockito.`when`(paymentCardHelper.generateAlias(cardNumber)).thenReturn("1234XXXXXXXX1234")
        val e = Exception()

        var handlerCalled = 0
        // When
        paymentCardAPI.registerCard("1111-1111", cardNumber, expiration, cxv) { optCard, optException ->
            // Then
            handlerCalled++
            Assert.assertNull(optCard)
            Assert.assertNotNull(optException)
            Assert.assertEquals(e, optException!!)
        }

        val captorPostCardRegisterHandler = argumentCaptor<(ClientCardRestAPI.CardRegistration?, Exception?) -> Unit>()
        verify(restAPI).postCardRegister(any(), any(),
                any(), captorPostCardRegisterHandler.capture())

        val cardRegistration = ClientCardRestAPI.CardRegistration(cardRegistrationId = "123",
        url = "https://example.com", preregistrationData = "preReg", accessKey = "key",
                tokenCard = "123-456")
        captorPostCardRegisterHandler.lastValue.invoke(cardRegistration, null)

        // Then
        val captorCardToRegister = argumentCaptor<ClientCardRestAPI.CardToRegister>()
        val captorHandler = argumentCaptor<(ClientCardRestAPI.CardToRegister?, Exception?) -> Unit>()
        verify(restAPI).getCardSafe(captorCardToRegister.capture(), captorHandler.capture())

        Assert.assertEquals(cardNumber, captorCardToRegister.lastValue.cardNumber)
        Assert.assertEquals(expiration, captorCardToRegister.lastValue.expiration)
        Assert.assertEquals(cxv, captorCardToRegister.lastValue.cvx)

        captorHandler.lastValue.invoke(null, e)
        Assert.assertEquals(1, handlerCalled)

    }

    @Test
    fun registerCard_postCardRegisterSuccess_getCardSafeCalledSuccess_postCardRegistrationDataFailed() {
        // Given
        Mockito.`when`(paymentCardHelper.generateAlias(cardNumber)).thenReturn("1234XXXXXXXX1234")
        val e = Exception()

        var handlerCalled = 0
        // When
        paymentCardAPI.registerCard("1111-1111", cardNumber, expiration, cxv) { optCard, optException ->
            // Then
            handlerCalled++
            Assert.assertNull(optCard)
            Assert.assertNotNull(optException)
            Assert.assertEquals(e, optException!!)
        }

        val captorPostCardRegisterHandler = argumentCaptor<(ClientCardRestAPI.CardRegistration?, Exception?) -> Unit>()
        verify(restAPI).postCardRegister(any(), any(),
                any(), captorPostCardRegisterHandler.capture())

        val cardRegistration = ClientCardRestAPI.CardRegistration(cardRegistrationId = "123",
                url = "https://example.com", preregistrationData = "preReg", accessKey = "key",
                tokenCard = "123-456")
        captorPostCardRegisterHandler.lastValue.invoke(cardRegistration, null)

        val captorCardSafeHandler = argumentCaptor<(ClientCardRestAPI.CardToRegister?, Exception?) -> Unit>()
        verify(restAPI).getCardSafe(any(), captorCardSafeHandler.capture())

        val cardToRegister = ClientCardRestAPI.CardToRegister(cardNumber, expiration, cxv)
        captorCardSafeHandler.lastValue.invoke(cardToRegister, null)


        // Then
        val captorCardToRegister = argumentCaptor<ClientCardRestAPI.CardToRegister>()
        val captorCardRegistration = argumentCaptor<ClientCardRestAPI.CardRegistration>()
        val captorHandler = argumentCaptor<(String?, Exception?) -> Unit>()
        verify(restAPI).postCardRegistrationData(captorCardToRegister.capture(), captorCardRegistration.capture(),
                captorHandler.capture())

        Assert.assertEquals(cardNumber, captorCardToRegister.lastValue.cardNumber)
        Assert.assertEquals(expiration, captorCardToRegister.lastValue.expiration)
        Assert.assertEquals(cxv, captorCardToRegister.lastValue.cvx)

        Assert.assertEquals("key", captorCardRegistration.lastValue.accessKey)
        Assert.assertEquals("123", captorCardRegistration.lastValue.cardRegistrationId)
        Assert.assertEquals("preReg", captorCardRegistration.lastValue.preregistrationData)
        Assert.assertEquals("123-456", captorCardRegistration.lastValue.tokenCard)
        Assert.assertEquals("https://example.com", captorCardRegistration.lastValue.url)

        captorHandler.lastValue.invoke(null, e)
        Assert.assertEquals(1, handlerCalled)

    }

    @Test
    fun registerCard_postCardRegisterSuccess_getCardSafeCalledSuccess_postCardRegistrationDataSuccess_putRegisterCardFailed() {
        // Given
        Mockito.`when`(paymentCardHelper.generateAlias(cardNumber)).thenReturn("1234XXXXXXXX1234")
        val e = Exception()

        var handlerCalled = 0
        // When
        paymentCardAPI.registerCard("1111-1111", cardNumber, expiration, cxv) { optCard, optException ->
            // Then
            handlerCalled++
            Assert.assertNull(optCard)
            Assert.assertNotNull(optException)
            Assert.assertEquals(e, optException!!)
        }

        val captorPostCardRegisterHandler = argumentCaptor<(ClientCardRestAPI.CardRegistration?, Exception?) -> Unit>()
        verify(restAPI).postCardRegister(any(), any(),
                any(), captorPostCardRegisterHandler.capture())

        val cardRegistration = ClientCardRestAPI.CardRegistration(cardRegistrationId = "123",
                url = "https://example.com", preregistrationData = "preReg", accessKey = "key",
                tokenCard = "123-456")
        captorPostCardRegisterHandler.lastValue.invoke(cardRegistration, null)

        val captorCardSafeHandler = argumentCaptor<(ClientCardRestAPI.CardToRegister?, Exception?) -> Unit>()
        verify(restAPI).getCardSafe(any(), captorCardSafeHandler.capture())

        val cardToRegister = ClientCardRestAPI.CardToRegister(cardNumber, expiration, cxv)
        captorCardSafeHandler.lastValue.invoke(cardToRegister, null)

        val captorPostCardRegistrationDataHandler = argumentCaptor<(String?, Exception?) -> Unit>()
        verify(restAPI).postCardRegistrationData(any(), any(), captorPostCardRegistrationDataHandler.capture())


        captorPostCardRegistrationDataHandler.lastValue.invoke("registration123", null)

        // Then
        val captorToken = argumentCaptor<String>()
        val captorCardRegistrationId = argumentCaptor<String>()
        val captorRegistration = argumentCaptor<String>()
        val captorHandler = argumentCaptor<(PaymentCardItem?, Exception?) -> Unit>()
        verify(restAPI).putRegisterCard(captorToken.capture(), captorCardRegistrationId.capture(),
                captorRegistration.capture(), captorHandler.capture())

        Assert.assertEquals("1111-1111", captorToken.lastValue)
        Assert.assertEquals("123", captorCardRegistrationId.lastValue)
        Assert.assertEquals("registration123", captorRegistration.lastValue)
        Assert.assertNotNull(captorHandler.lastValue)


        captorHandler.lastValue.invoke(null, e)
        Assert.assertEquals(1, handlerCalled)


    }

    @Test
    fun registerCard_Success() {
        // Given
        Mockito.`when`(paymentCardHelper.generateAlias(cardNumber)).thenReturn("1234XXXXXXXX1234")
        val card = PaymentCardItem("123456", "1234XXXXXXXX1234", "123", "EUR",
                true, "GOOD", "ABC", Calendar.getInstance().time)

        var handlerCalled = 0
        // When
        paymentCardAPI.registerCard("1111-1111", cardNumber, expiration, cxv) { optCard, optException ->
            // Then
            handlerCalled++
            Assert.assertNotNull(optCard)
            Assert.assertNull(optException)
            Assert.assertEquals(card, optCard!!)
        }

        val captorPostCardRegisterHandler = argumentCaptor<(ClientCardRestAPI.CardRegistration?, Exception?) -> Unit>()
        verify(restAPI).postCardRegister(any(), any(),
                any(), captorPostCardRegisterHandler.capture())

        val cardRegistration = ClientCardRestAPI.CardRegistration(cardRegistrationId = "123",
                url = "https://example.com", preregistrationData = "preReg", accessKey = "key",
                tokenCard = "123-456")
        captorPostCardRegisterHandler.lastValue.invoke(cardRegistration, null)

        val captorCardSafeHandler = argumentCaptor<(ClientCardRestAPI.CardToRegister?, Exception?) -> Unit>()
        verify(restAPI).getCardSafe(any(), captorCardSafeHandler.capture())

        val cardToRegister = ClientCardRestAPI.CardToRegister(cardNumber, expiration, cxv)
        captorCardSafeHandler.lastValue.invoke(cardToRegister, null)

        val captorPostCardRegistrationDataHandler = argumentCaptor<(String?, Exception?) -> Unit>()
        verify(restAPI).postCardRegistrationData(any(), any(), captorPostCardRegistrationDataHandler.capture())


        captorPostCardRegistrationDataHandler.lastValue.invoke("registration123", null)

        // Then
        val captorHandler = argumentCaptor<(PaymentCardItem?, Exception?) -> Unit>()
        verify(restAPI).putRegisterCard(any(), any(),
                any(), captorHandler.capture())

        captorHandler.lastValue.invoke(card, null)
        Assert.assertEquals(1, handlerCalled)

    }

}