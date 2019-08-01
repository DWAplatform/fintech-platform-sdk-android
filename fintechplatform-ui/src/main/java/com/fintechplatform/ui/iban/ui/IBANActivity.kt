package com.fintechplatform.ui.iban.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R

/**
 * UI for IBAN account number and mandatory personal data linked to.
 */
class IBANActivity: FragmentActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frag = IBANFragment.newInstance()

        setContentView(R.layout.activity_with_fragment)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, frag)
                .commit()

    }
}