package com.dwaplatform.android

import android.content.Context
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class DWAplatformTest {

    @Test
    fun test_getCardAPI_MissingConfiguration() {

        val context = Mockito.mock(Context::class.java)

        try {
            DWAplatform.getCardAPI(context)
            Assert.fail()
        } catch (e: Exception) {
            Assert.assertTrue(e is Exception)
        }
    }

}