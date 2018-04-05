package com.fintechplatform.api.card.helpers

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PaymentPaymentCardHelperTest {

    lateinit var paymentCardHelper: PaymentCardHelper

    @Before
    fun setUp() {
        paymentCardHelper = PaymentCardHelper(SanityCheck())
    }

    @Test
    fun test_generateAlias() {
        // When
        val result = paymentCardHelper.generateAlias("1111222233334444")

        // Then
        Assert.assertNotEquals("1234123412341234", result)
        Assert.assertTrue(result.contains("XXXXXX"))
        Assert.assertEquals(16, result.length)

    }

    @Test
    fun test_checkCardNumberFormat_Correct() {

        // When
        paymentCardHelper.checkCardNumberFormat("1111222233334444")
        paymentCardHelper.checkCardNumberFormat("1234123412341234")

        // Then never throw
    }

    @Test
    fun test_checkCardNumberFormat_WrongFormat() {
        try {
            // When
            paymentCardHelper.checkCardNumberFormat("12341234112341234")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardNumberFormat("123412A412341234")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardNumberFormat("123412341234123-")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }


    }

    @Test
    fun test_checkExpirationFormat_Correct() {

        // When
        paymentCardHelper.checkCardExpirationFormat("1122")
        paymentCardHelper.checkCardExpirationFormat("8899")

        // Then never throw
    }

    @Test
    fun test_checkExpirationFormat_WrongFormat() {
        try {
            // When
            paymentCardHelper.checkCardExpirationFormat("11223")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardExpirationFormat("1A23")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardExpirationFormat(" 123")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }


    }

    @Test
    fun test_checkCardCXVFormat_Correct() {

        // When
        paymentCardHelper.checkCardCXVFormat("123")
        paymentCardHelper.checkCardCXVFormat("999")

        // Then never throw
    }

    @Test
    fun test_checkCardCXVFormat_WrongFormat() {
        try {
            // When
            paymentCardHelper.checkCardCXVFormat("1123")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardCXVFormat("A12")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardCXVFormat("  12")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }
    }

    @Test
    fun test_checkCardFormat_Correct() {

        // When
        paymentCardHelper.checkCardFormat("1234123412341234", "1122", "123")
        paymentCardHelper.checkCardFormat("1111222233334444", "9988", "999")

        // Then never throw
    }

    @Test
    fun test_checkCardFormat_WrongFormat() {
        try {
            // When
            paymentCardHelper.checkCardFormat("12341234123412346", "1122", "123")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardFormat("1234123412341234", "112A", "123")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }

        try {
            // When
            paymentCardHelper.checkCardFormat("1234123412341234", "1122", "13")
            Assert.fail()
        } catch(e: SanityCheckException) {
            // Then
            Assert.assertNotNull(e.message)
        }


    }


}