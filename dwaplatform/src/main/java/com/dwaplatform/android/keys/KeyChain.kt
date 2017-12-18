package com.dwaplatform.android.keys

import android.content.Context

/**
 * Key Chain helper class to set and key key-values from/to android shared preferences
 */
class KeyChain {
    operator fun set(key: String, value: String, context: Context) {
        val sharedPref = context.getSharedPreferences("com.dwaplatform.android.KeyChain",
                Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    operator fun get(key: String, context: Context): String {
        val sharedPref = context.getSharedPreferences("com.dwaplatform.android.KeyChain",
                Context.MODE_PRIVATE)

        return sharedPref.getString(key, "")
    }
}