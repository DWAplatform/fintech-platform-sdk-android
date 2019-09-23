package com.fintechplatform.ui.enterprise.contacts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * Shows email and telephone contacts
 */
class EnterpriseContactsActivity : AppCompatActivity(), EnterpriseContactsContract.Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)

        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                val frag = EnterpriseContactsFragment.newInstance(hostname, dataAccount)
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.contentContainer, frag, EnterpriseContactsFragment::class.java.canonicalName)
                        .commit()
            }
        }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun backFromEnterpriseContacts() {
        onBackPressed()
    }
}