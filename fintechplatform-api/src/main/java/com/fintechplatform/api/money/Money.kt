package com.fintechplatform.api.money

class Money(val value: Long, val currency: String = "EUR") {

    override fun toString(): String {
        return toString(",")
    }

    fun toString(separator: String): String {

        var value = this.value
        var sign = ""
        if (value < 0) {
            sign = "- "
            value = (-value)
        }

        if (value < 10)
            return sign + "0" + separator + "0" + value
        else if (value < 100)
            return sign + "0" + separator + value
        else {
            val i = value / 100

            var si = ""
            if (i < 1000) {
                si = i.toString()
            } else {
                val sb = StringBuffer()
                val full = i.toString()
                val firstp = full.substring(0, full.length - 3)
                val secondp = full.substring(firstp.length, full.length)
                sb.append(firstp)
                sb.append('.')
                sb.append(secondp)

                si = sb.toString()
            }

            val f = value % 100

            var sf = f.toString()
            if (f < 10)
                sf = "0" + sf

            val res = sign + si + separator + sf
            return res
        }
    }

    companion object {

        fun verifiedValueOf(money: String): Money? {
            val limit = 99999999

            val newmoney = money.replace(',', '.')

            // check number of digits after .

            val dotindex = newmoney.indexOf('.')
            if (dotindex == -1) {
                var integ: Long
                try {
                    integ = Integer.parseInt(newmoney).toLong()
                } catch(e: Exception) {
                    integ = 0
                }
                val iv = integ * 100
                return if (iv > limit) null else Money(iv)
            }


            val strinteg = newmoney.substring(0, dotindex)
            val strdecimals = newmoney.substring(dotindex + 1)

            if (strdecimals.length > 2) {
                return null
            }

            val strdecimalssanit = if (strdecimals.length == 0) "0" else strdecimals

            val integer: Long
            val decimal: Long
            try {
                integer = Integer.parseInt(strinteg).toLong()
                decimal = Integer.parseInt(strdecimalssanit).toLong()
            } catch(e: Exception) {
                return null
            }

            val adjdecimal = if (strdecimals.length == 1) decimal * 10 else decimal

            val iv = integer * 100 + adjdecimal
            return if (iv > limit) null else Money(iv)
        }


        fun valueOf(money: String): Money {
            return verifiedValueOf(money) ?: Money(0L)
        }
    }
}
