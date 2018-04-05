package com.fintechplatform.android.email

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailHelper {
    private val pattern: Pattern
    private var matcher: Matcher? = null
    private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    init {
        pattern = Pattern.compile(EMAIL_PATTERN)
    }

    /**
     * Validate hex with regular expression

     * @param hex
     * *            hex for validation
     * *
     * @return true valid hex, false invalid hex
     */
    fun validate(hex: String): Boolean {

        matcher = pattern.matcher(hex)
        return matcher!!.matches()

    }
}