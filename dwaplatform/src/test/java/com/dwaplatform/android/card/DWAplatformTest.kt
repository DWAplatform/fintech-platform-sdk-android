package com.dwaplatform.android.card

import android.content.Context
import com.dwaplatform.android.DWAplatform
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by tcappellari on 08/12/2017.
 */
class DWAplatformTest {


    @Test
    fun test_balanceHelper() {
        val context = Mockito.mock(Context::class.java)

        val hostName = "https://api.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTI3NDU0MDAsImlhdCI6MTUxMjY1OTAwMCwiY2xpZW50SWQiOiJkYmI3YzEwYS03MzY3LTExZTctODgxMy1iZmUyY2ExMGJiOWEifQ.A1z1-uMuMdq_CrbNjT7xhzrUdJQ1cIIjoLFUqTIi63JUFi0fmkE0CxW-iVemo2VR8qyJjXW6CljJ-8p3cy0V5A"

        val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"

        val balanceBuilder = DWAplatform.buildBalance()
        val balanceHelperComponent = balanceBuilder.createBalanceHelperComponent(hostName, token, context)

        balanceHelperComponent.balanceHelper.getAndUpdateCachedBalance(userId, accountId) { optMoney, optError ->

            //



        }


    }
}