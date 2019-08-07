package com.fintechplatform.ui.iban.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * UI for IBAN account number and mandatory personal data linked to.
 */
class IBANActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)

        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let {dataAccount ->
                val frag = IBANFragment.newInstance(hostname, dataAccount)
                supportFragmentManager.beginTransaction()
                        .add(R.id.contentContainer, frag)
                        .commit()
            }
        }
    }
}