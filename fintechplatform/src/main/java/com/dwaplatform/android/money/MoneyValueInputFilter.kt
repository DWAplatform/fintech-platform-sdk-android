package com.dwaplatform.android.money

import android.text.Spanned
import android.text.method.DigitsKeyListener

/**
 * Created by ingrid on 06/12/17.
 */

class MoneyValueInputFilter : DigitsKeyListener(false, true) {

    override fun filter(source: CharSequence,
                        start: Int,
                        end: Int,
                        destination: Spanned,
                        destinationStart: Int,
                        destinationEnd: Int): CharSequence? {
        if (end > start) {
            val destinationString = destination.toString()
            val resultingTxt = destinationString.substring(0, destinationStart) +
                    source.subSequence(start, end) +
                    destinationString.substring(destinationEnd)
            if (Money.verifiedValueOf(resultingTxt) == null) return "" else return null
        }
        return null
    }
}