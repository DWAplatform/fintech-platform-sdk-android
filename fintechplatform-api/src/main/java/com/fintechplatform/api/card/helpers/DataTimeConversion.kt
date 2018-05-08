package com.fintechplatform.api.card.helpers

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date time conversion from and to RFC3339 standard
 */
object DateTimeConversion {
    val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val formatDefault = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"

    fun convert2RFC3339(ts: Date, format: String = formatDefault): String =
        SimpleDateFormat(format).format(ts)


    fun convertFromRFC3339(str: String): Date? {
        try {
            return Date(SimpleDateFormat(format).parse(str).time)
        } catch(e: Exception) {
           return  convertFromRFC3339Fallback(str)
        }
    }

    private fun convertFromRFC3339Fallback(str: String): Date? {
        try {
            return Date(SimpleDateFormat(formatDefault).parse(str).time)
        } catch(e: Exception) {
            return  null
        }
    }
}
