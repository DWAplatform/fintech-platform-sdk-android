package com.fintechplatform.ui.iban

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * UI for IBAN account number and mandatory personal data linked to.
 */
class IBANActivity: FragmentActivity(), IBANContract.Navigation {
    private data class IntentContent(val hostName: String, val dataAccount: DataAccount)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)

        var frag: IBANFragment? = null

        savedInstanceState?.let{ extras ->
            frag = supportFragmentManager.findFragmentByTag(IBANFragment::class.java.canonicalName) as IBANFragment
        }

        getExtras()?.let {intent ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, IBANFragment::class.java.canonicalName)
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, IBANFragment.newInstance(intent.hostName, intent.dataAccount), IBANFragment::class.java.canonicalName)
                    .commit()
        }
    }

    private fun getExtras(): IntentContent? = intent.extras?.getString("hostname")?.let { hostname ->
        intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
            IntentContent(hostname, dataAccount)
        }
    }

    override fun backwardFromIBAN() {
         finish()
    }
}