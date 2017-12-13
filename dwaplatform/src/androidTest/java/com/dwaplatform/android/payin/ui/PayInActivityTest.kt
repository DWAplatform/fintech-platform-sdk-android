package com.dwaplatform.android.payin.ui

import org.junit.Assert.*

import android.app.Instrumentation
import android.app.Instrumentation.ActivityMonitor
import android.content.Context
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.inputmethod.InputMethodManager
import com.dwaplatform.android.R
import com.dwaplatform.android.payin.PayInActivity
import com.dwaplatform.android.payin.PayInUIMockViewComponent
import com.dwaplatform.android.payin.models.PayInConfiguration
import com.dwaplatform.android.secure3d.ui.Secure3DActivity
import com.nhaarman.mockito_kotlin.times
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Created by ingrid on 12/12/17.
 */




/**
 * Created by ingrid on 08/09/17.
 */

class PayInActivityTest {

    @Rule @JvmField val main = ActivityTestRule(PayInActivity::class.java, false, false)
    @Rule @JvmField val uiThreadTestRule = UiThreadTestRule()

    @Before fun setup() {
        PayInUIMockViewComponent()
    }

    @Test
    fun testOnCreateViewWithInitialAmount(){
        val initialAmount = 1000L
        val i = Intent()
        i.putExtra("initialAmount", initialAmount)
        main.launchActivity(i)

        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
        Mockito.verify(main.activity.presenter).initialize(initialAmount)
        Mockito.verify(main.activity.presenter).refresh()
    }

    @Test
    fun testOnCreateViewWithoutInitialAmount(){
        main.launchActivity(Intent())
        Mockito.verify(main.activity.presenter).initialize(null)
        Mockito.verify(main.activity.presenter).refresh()
    }

    @Test
    fun testOnEditingChange(){
        //Given
        main.launchActivity(Intent())

        // When
        Espresso.onView(ViewMatchers.withId(R.id.amountText)).perform(ViewActions.typeText("10.00"))

        // Then
        Mockito.verify(main.activity.presenter, times(5)).onEditingChanged()
    }

    @Test
    fun testSetForwardTextConfirm() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setForwardTextConfirm()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(ViewMatchers.withText("Conferma")))

    }

    @Test
    fun testSetForwardButtonPayInCC() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setForwardButtonPayInCC()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.payin_cc)))

    }

    @Test
    fun testSetAmount() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setAmount("125")
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.amountText)).perform(ViewActions.typeText("125,00"))

    }

    @Test
    fun testGetAmount() {
        // Given
        main.launchActivity(Intent())

        // When
        Espresso.onView(ViewMatchers.withId(R.id.amountText)).perform(ViewActions.typeText("10.00"))

        // Then
        Assert.assertEquals("10.00", main.activity.getAmount())

    }

    @Test
    fun testSetForward() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setForward("Test")
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(ViewMatchers.withText("Test")))

    }

    @Test
    fun testForwardEnable() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.forwardEnable()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))

    }

    @Test
    fun testForwardDisable() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setForward("Test")
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.forwardButton)).check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))

    }

    @Test
    fun testSetNewBalanceAmount() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setNewBalanceAmount("Test")
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.newBalanceAmountLabel)).check(ViewAssertions.matches(ViewMatchers.withText("Test")))

    }

    @Test
    fun testSetFeeAmount() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.setFeeAmount("Test")
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.feeAmountLabel)).check(ViewAssertions.matches(ViewMatchers.withText("Test")))

    }

    @Test
    fun testShowCommunicationWait() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.showCommunicationWait()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.activityIndicator)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testHideCommunicationWait() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.hideCommunicationWait()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.activityIndicator)).check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))

    }

    @Test
    fun testShowCommunicationInternalError() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.showCommunicationInternalError()
        }

        // Then
        Espresso.onView(ViewMatchers.withText("Errore connessione internet")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testShowCommunicationIdemoptencyError() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.showIdempotencyError()
        }

        // Then
        Espresso.onView(ViewMatchers.withText("Richiesta già inviata")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testGoBack() {
        // Given
        main.launchActivity(Intent())

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.goBack()
        }

        // Then
        Thread.sleep(5000)
        Assert.assertTrue(main.activity.isDestroyed())

    }

    @Test
    fun testGoToCreditCard() {
        //fixme how to verify launchActivity
        // Given
        main.launchActivity(Intent())
//        val activityMonitor = ActivityMonitor(CreditCardActivity::class.simpleName, null, false)

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.goToCreditCard()
        }

        // Then

    }

    @Test
    fun testGoToSecure3D() {
        //fixme how to verify launchActivity
        // Given
        main.launchActivity(Intent())
        val activityMonitor = ActivityMonitor(Secure3DActivity::class.simpleName, null, false)

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.goToSecure3D("http://www.google.com")
        }

        // Then
        val nextActivity = Instrumentation().waitForMonitorWithTimeout(activityMonitor, 10000)
        Assert.assertNotNull(nextActivity)
        nextActivity .finish()

    }

    @Test
    fun testShowkeyboardAmount() {
        //fixme how to verify if softkeyboard is displayed
        // Given
        main.launchActivity(Intent())
        val keyboard = main.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.showKeyboardAmount()
        }

        // Then
        Assert.assertTrue(keyboard.isActive)
    }

    @Test
    fun testHidekeyboardAmount() {
        //Given
        main.launchActivity(Intent())
        Espresso.onView(ViewMatchers.withId(R.id.amountText)).perform(ViewActions.typeText("105"))

        //When
        uiThreadTestRule.runOnUiThread { main.activity.hideKeyboard() }

        //Then
        Espresso.onView(ViewMatchers.withId(R.id.amountText)).perform(ViewActions.closeSoftKeyboard())
    }
}