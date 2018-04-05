package com.fintechplatform.ui.secure3d.ui

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.UiThreadTestRule
import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.activity_secure3_d.*
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class Secure3DActivityTest{
    @Rule
    @JvmField val main = ActivityTestRule(Secure3DActivity::class.java, false, false)
    @Rule
    @JvmField val uiThreadTestRule = UiThreadTestRule()

    @Before
    fun setup() {
        Secure3DMockUI()
    }

    @Test
    fun onCreateViewWithInitialUrl(){
        // When
        val redirecturl = "www.google.com"
        val i = Intent()
        i.putExtra("redirecturl", redirecturl)
        main.launchActivity(i)

        // Then
        Mockito.verify(main.activity.presenter).initialize(redirecturl)
        Espresso.onView(ViewMatchers.withId(R.id.webview)).check(ViewAssertions.matches(ViewMatchers.isJavascriptEnabled()))

        Espresso.onView(ViewMatchers.withId(R.id.webview)).check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))

        // Mockito.verify(main.activity.presenter).onPageStarted()

        //Mockito.verify(main.activity.presenter).onPageFinished(redirecturl)
        //Espresso.onView(ViewMatchers.withId(R.id.activityIndicator)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @Test
    fun onLoadUrlPageStarted(){
        // When
        val redirecturl = "http://www.example.com/"
        val i = Intent()
        i.putExtra("redirecturl", redirecturl)
        main.launchActivity(i)

        uiThreadTestRule.runOnUiThread {
            main.activity.webview.loadUrl(redirecturl)
        }

        // Then
        Thread.sleep(5000)
        Mockito.verify(main.activity.presenter).onPageStarted()
        Mockito.verify(main.activity.presenter).onPageFinished(redirecturl)
    }

    @Test
    fun testShowCommunicationWait() {
        // Given
        // When
        val redirecturl = "http://www.example.com/"
        val i = Intent()
        i.putExtra("redirecturl", redirecturl)
        main.launchActivity(i)

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
        // When
        val redirecturl = "http://www.example.com/"
        val i = Intent()
        i.putExtra("redirecturl", redirecturl)
        main.launchActivity(i)

        // When
        uiThreadTestRule.runOnUiThread {
            main.activity.hideCommunicationWait()
        }

        // Then
        Espresso.onView(ViewMatchers.withId(R.id.activityIndicator)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))

    }

}