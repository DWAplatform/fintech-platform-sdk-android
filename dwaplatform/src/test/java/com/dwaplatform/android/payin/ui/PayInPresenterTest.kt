package com.dwaplatform.android.payin.ui

import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.balance.api.BalanceAPI
import com.dwaplatform.android.account.balance.db.BalancePersistence
import com.dwaplatform.android.account.balance.helpers.BalanceHelper
import com.dwaplatform.android.account.balance.models.BalanceItem
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.money.MoneyHelper
import com.dwaplatform.android.payin.PayInContract
import com.dwaplatform.android.payin.PayInPresenter
import com.dwaplatform.android.payin.api.PayInAPI
import com.dwaplatform.android.payin.models.PayInConfiguration
import com.dwaplatform.android.payin.models.PayInReply
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.never
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.mockito.*

/**
 * Created by ingrid on 12/12/17.
 */

class PayInPresenterTest {

    lateinit var presenter: PayInPresenter

    @Mock lateinit var view: PayInContract.View
    @Mock lateinit var api: PayInAPI
    var moneyHelper = MoneyHelper()
    private var hostName = "myhostname.com"

    @Mock lateinit var balancePersistence: BalancePersistence
    @Mock lateinit var balanceAPI: BalanceAPI

    @Mock lateinit var config: PayInConfiguration
    @Mock lateinit var feeHelper: FeeHelper

    @Captor lateinit var userIdCaptor: ArgumentCaptor<String>
    @Captor lateinit var accountIdCaptor: ArgumentCaptor<String>
    @Captor lateinit var creditcardidCaptor: ArgumentCaptor<String>
    @Captor lateinit var amountCaptor: ArgumentCaptor<Long>
    @Captor lateinit var idempotencyCaptor: ArgumentCaptor<String>
    @Captor lateinit var callbackCaptor: ArgumentCaptor<(PayInReply?, Exception?) -> Unit>
    @Captor lateinit var callbackBalanceCaptor: ArgumentCaptor<(Long?, Exception?) -> Unit>

    @Captor lateinit var balanceItemCaptor: ArgumentCaptor<BalanceItem>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        config = PayInConfiguration("userid", "accountid", "paymentcardid")
        presenter = PayInPresenter(config, view, api, moneyHelper, BalanceHelper(balancePersistence, balanceAPI), feeHelper)
    }

    @Test
    fun initializeWithInitialAmountGreaterThanZero() {
        //Given
        config = PayInConfiguration("userid", "accountid", "paymentcardid")
        Mockito.`when`(view.getAmount()).thenReturn("10,00")

        val balanceItem = BalanceItem("", Money(1000L))

        Mockito.`when`(balancePersistence.getBalanceItem("accountid")).thenReturn(balanceItem)

        Mockito.`when`(feeHelper.calcPayInFee(1000L)).thenReturn(100L)

        //When
        val initialAmount = 1000L
        presenter.initialize(initialAmount)

        //Then
        Assert.assertNotNull(presenter.idempotencyPayin)
        Mockito.verify(view).setAmount("10,00")
        Mockito.verify(view).forwardEnable()
        Mockito.verify(view).setNewBalanceAmount("20,00 €")
        Mockito.verify(view).setFeeAmount("1,00 €")

    }

    @Test
    fun initializeWithInitialAmountZero() {

        //Given
        Mockito.`when`(view.getAmount()).thenReturn("0,00")

        val balanceItem = BalanceItem("", Money(1000L))

        Mockito.`when`(balancePersistence.getBalanceItem("accountid")).thenReturn(balanceItem)

        Mockito.`when`(feeHelper.calcPayInFee(0L)).thenReturn(0L)

        //When
        val initialAmount = 0L
        presenter.initialize(initialAmount)

        //Then
        Assert.assertNotNull(presenter.idempotencyPayin)
        Mockito.verify(view).setAmount("0,00")
        Mockito.verify(view).forwardEnable()
        Mockito.verify(view).setNewBalanceAmount("10,00 €")
        Mockito.verify(view).setFeeAmount("0,00 €")

    }

    @Test
    fun initializeWithoutInitialAmount() {
        //Given
        Mockito.`when`(view.getAmount()).thenReturn("")
        val balanceItem = BalanceItem("", Money(1000L))

        Mockito.`when`(balancePersistence.getBalanceItem("accountid")).thenReturn(balanceItem)

        Mockito.`when`(feeHelper.calcPayInFee(0L)).thenReturn(0L)

        //When
        presenter.initialize(null)

        //Then
        Assert.assertNotNull(presenter.idempotencyPayin)
        Mockito.verify(view).forwardDisable()
        Mockito.verify(view).setNewBalanceAmount("10,00 €")
        Mockito.verify(view).setFeeAmount("0,00 €")
    }

    @Test
    fun refreshWithCreditCard() {
        //Given


        //When
        presenter.refresh()

        //Then
        Mockito.verify(view).showKeyboardAmount()
        Mockito.verify(view).setForwardTextConfirm()

        Mockito.verify(balanceAPI).balance(capture(userIdCaptor), capture(accountIdCaptor), capture(callbackBalanceCaptor))

        Assert.assertEquals(accountIdCaptor.value, "accountid")
        Assert.assertEquals(userIdCaptor.value, "userid")
        Assert.assertNotNull(callbackBalanceCaptor)
    }

    @Test
    fun refreshWithoutCreditCard() {
        //Given
        config = PayInConfiguration("userid", "accountid", null)
        presenter = PayInPresenter(config, view, api, moneyHelper, BalanceHelper(balancePersistence, balanceAPI), feeHelper)

        //When
        presenter.refresh()

        //Then
        Mockito.verify(view).showKeyboardAmount()
        Mockito.verify(view).setForwardButtonPayInCC()
        Mockito.verify(balanceAPI).balance(capture(userIdCaptor), capture(accountIdCaptor), capture(callbackBalanceCaptor))

        Assert.assertEquals(userIdCaptor.value, "userid")
        Assert.assertEquals(accountIdCaptor.value, "accountid")
        Assert.assertNotNull(callbackBalanceCaptor)
    }
    /*
            @Test
            fun refreshBalanceServerError() {
                //Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("12345%O(//=()(")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")


                val balanceItem = BalanceItem(1000L)
                Mockito.`when`(balanceHelper.getBalanceItem()).thenReturn(balanceItem)

                Mockito.`when`(feeHelper.calcPayInFee(0L)).thenReturn(0L)

                //When
                presenter.refresh()

                //Then
                Mockito.verify(view).showKeyboardAmount()
                Mockito.verify(view).setForwardTextConfirm()
                Mockito.verify(api).balance(capture(userIdCaptor), capture(callbackBalanceCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertNotNull(callbackBalanceCaptor)

                /* Callback test */
                // When
                callbackBalanceCaptor.value.invoke(null, Exception())

                // Then
                Mockito.verify(balanceHelper, never()).saveBalance(any())
                Mockito.verify(view, never()).setNewBalanceAmount(any())
            }

            @Test
            fun refreshBalanceServerOkNullBalance() {
                //Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("12345%O(//=()(")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")

                //When
                presenter.refresh()

                //Then
                Mockito.verify(view).showKeyboardAmount()
                Mockito.verify(view).setForwardTextConfirm()
                Mockito.verify(api).balance(capture(userIdCaptor), capture(callbackBalanceCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertNotNull(callbackBalanceCaptor)

                /* Callback test */
                // When
                callbackBalanceCaptor.value.invoke(null, null)

                // Then
                Mockito.verify(balanceHelper, never()).saveBalance(any())
                Mockito.verify(view, never()).setNewBalanceAmount(any())
            }

            @Test
            fun refreshBalanceServerOkWithBalance() {
                //Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("12345%O(//=()(")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")

                //When
                presenter.refresh()

                //Then
                Mockito.verify(view).showKeyboardAmount()
                Mockito.verify(view).setForwardTextConfirm()
                Mockito.verify(api).balance(capture(userIdCaptor), capture(callbackBalanceCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertNotNull(callbackBalanceCaptor)

                /* Callback test */
                // Given
                val balanceReloaded = 10000L

                val balanceItem = BalanceItem(balanceReloaded)
                Mockito.`when`(balanceHelper.getBalanceItem()).thenReturn(balanceItem)

                Mockito.`when`(view.getAmount()).thenReturn("6,00")


                // When
                callbackBalanceCaptor.value.invoke(balanceReloaded, null)

                // Then
                Mockito.verify(balanceHelper).saveBalance(capture(balanceItemCaptor))
                Assert.assertEquals(balanceReloaded, balanceItemCaptor.value.balance)

                Mockito.verify(view).setNewBalanceAmount("106,00 €")
            }

            @Test
            fun onEditingChangedWithoutCreditCardAmountGreaterThenZero() {
                //Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn(null)

                val balanceItem = BalanceItem(1000L)
                Mockito.`when`(balanceHelper.getBalanceItem()).thenReturn(balanceItem)

                Mockito.`when`(view.getAmount()).thenReturn("6,00")

                Mockito.`when`(feeHelper.calcPayInFee(600L)).thenReturn(100L)

                // When
                presenter.onEditingChanged()

                // Then
                Mockito.verify(view).forwardEnable()
                Mockito.verify(view).setNewBalanceAmount("16,00 €")
                Mockito.verify(view).setFeeAmount("1,00 €")
            }

            @Test
            fun onConfirmWithoutCardDataBalanceEnough() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn(null)

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).setForward("")
                Mockito.verify(view).goToCreditCard()
            }

            @Test
            fun onConfirmWithCardData() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)
            }

            @Test
            fun onConfirmWithCardDataServerIdempotencyError() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)

                /* Callback test */
                // When
                callbackCaptor.value.invoke(null, api.IdempotencyError(Exception()))

                // Then
                Mockito.verify(view).showIdempotencyError()
                Mockito.verify(view, never()).goBack()
                Mockito.verify(view, never()).goToSecure3D(any())
            }

            @Test
            fun onConfirmWithCardDataServerCommunicationInternalError() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)

                /* Callback test */
                // When
                callbackCaptor.value.invoke(null, api.GenericCommunicationError(Exception()))

                // Then
                Mockito.verify(view).showCommunicationInternalError()
                Mockito.verify(view, never()).goBack()
                Mockito.verify(view, never()).goToSecure3D(any())
            }

            @Test
            fun onConfirmWithCardDataServerNoData() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)

                /* Callback test */
                // When
                callbackCaptor.value.invoke(null, null)

                // Then
                Mockito.verify(view).showIdempotencyError()
                Mockito.verify(view, never()).goBack()
                Mockito.verify(view, never()).goToSecure3D(any())
            }

            @Test
            fun onConfirmWithCardDataServerWithSecureCode() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)

                /* Callback test */
                // Given
                val payinReply = PayInReply("123", true, "http://www.example.com")

                // When
                callbackCaptor.value.invoke(payinReply, null)

                // Then
                Mockito.verify(view).goToSecure3D("http://www.example.com")
                Mockito.verify(view, never()).goBack()
            }

            @Test
            fun onConfirmWithCardDataServerPayInSuccess() {
                // Given
                Mockito.`when`(dbccardhelper.creditCardId()).thenReturn("1234123412341234")
                Mockito.`when`(view.getAmount()).thenReturn("10,00")
                Mockito.`when`(dbUsersHelper.userid()).thenReturn("1234567890")
                presenter.idempotencyPayin = "ABC"

                // When
                presenter.onConfirm()

                // Then
                Mockito.verify(view).forwardDisable()
                Mockito.verify(view).showCommunicationWait()

                Mockito.verify(api).payIn(
                        capture(userIdCaptor),
                        capture(creditcardidCaptor),
                        capture(amountCaptor),
                        capture(idempotencyCaptor),
                        capture(callbackCaptor))

                Assert.assertEquals(userIdCaptor.value, "1234567890")
                Assert.assertEquals(creditcardidCaptor.value, "1234123412341234")
                Assert.assertEquals(amountCaptor.value, 1000L)
                Assert.assertEquals(idempotencyCaptor.value, "ABC")
                Assert.assertNotNull(callbackCaptor.value)

                /* Callback test */
                // Given
                val payinReply = PayInReply("123", false, null)

                // When
                callbackCaptor.value.invoke(payinReply, null)

                // Then
                Mockito.verify(view, never()).goToSecure3D(any())
                Mockito.verify(view).goBack()
            }
        */
    @Test
    fun onAbortClick() {
        // When
        presenter.onAbortClick()

        // Then
        Mockito.verify(view).hideKeyboard()
        Mockito.verify(view).goBack()
    }

}