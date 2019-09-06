package com.fintechplatform.ui.payout

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * Payment from user emoney to registered bank account
 */
class PayOutActivity: FragmentActivity(), PayOutContract.Navigation {

    private data class IntentContent(val hostName: String, val dataAccount: DataAccount)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var frag: PayOutFragment? = null

        savedInstanceState?.let{ extras ->
            frag = supportFragmentManager.findFragmentByTag(PayOutFragment::class.java.canonicalName) as PayOutFragment
        }

        getExtras()?.let {intent ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, PayOutFragment::class.java.canonicalName)
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, PayOutFragment.newInstance(intent.hostName, intent.dataAccount), PayOutFragment::class.java.canonicalName)
                    .commit()
        }
        
        setContentView(R.layout.activity_with_fragment)

    }

    private fun getExtras(): IntentContent? = intent.extras?.getString("hostname")?.let { hostname ->
        intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                IntentContent(hostname, dataAccount)
        }
    }

    override fun backforwardFromPayOut() {
        finish()
    }

    override fun goToIBANAddress() {
        // TODO goToIBanFragment
    }
}