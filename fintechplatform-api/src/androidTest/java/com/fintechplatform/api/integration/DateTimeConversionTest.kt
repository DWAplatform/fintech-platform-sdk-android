package com.fintechplatform.api.integration

import android.support.test.runner.AndroidJUnit4
import com.fintechplatform.api.card.helpers.DateTimeConversion
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by tcappellari on 08/05/2018.
 */
@RunWith(AndroidJUnit4::class)
class DateTimeConversionTest {


    /**
     * We test DateTimeConversion on Instrument Test also, because
     * we got different result from SimpleDateFormat on unit test and
     * SimpleDateFormat on Instrument Test.
     */
    @Test
    fun test_convertFromAndToRFC3339() {
        val dateStrType1 = "2018-05-07T13:54:55+02:00"
        // When
        val resultDateType1 = DateTimeConversion.convertFromRFC3339(dateStrType1)
        Assert.assertNotNull(resultDateType1)

        val resultDateStrType1 = DateTimeConversion.convert2RFC3339(resultDateType1!!,
                DateTimeConversion.formatDefault)

        // Then
        Assert.assertEquals(resultDateStrType1, dateStrType1)

        val dateStrType2 = "2018-05-08T05:40:39Z"
        // When
        val resultDateType2 = DateTimeConversion.convertFromRFC3339(dateStrType2)
        Assert.assertNotNull(resultDateType2)

        val resultDateStrType2 = DateTimeConversion.convert2RFC3339(resultDateType2!!,
                DateTimeConversion.format)

        // Then
        Assert.assertEquals(resultDateStrType2, dateStrType2)


    }


}