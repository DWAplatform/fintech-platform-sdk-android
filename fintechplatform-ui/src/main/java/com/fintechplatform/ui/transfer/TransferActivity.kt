package com.fintechplatform.ui.transfer

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * Peer to Peer transfer, based on p2p user selected
 */
class TransferActivity: FragmentActivity(),
        TransferContract.Navigation {

    private data class IntentContent(val hostName: String, val dataAccount: DataAccount)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_with_fragment)

        var frag: TransferFragment? = null

        val transferTo = intent.extras?.run {
            this.getParcelable<DataAccount>("transferTo")
        }?: throw Exception("Mandatory parameter: DataAccount transferTo")

        savedInstanceState?.let{ extras ->
            frag = supportFragmentManager.findFragmentByTag(TransferFragment::class.java.canonicalName) as TransferFragment
        }

        getExtras()?.let {intent ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, TransferFragment::class.java.canonicalName)
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, TransferFragment.newInstance(intent.hostName, intent.dataAccount, transferTo), TransferFragment::class.java.canonicalName)
                    .commit()
        }
    }

    private fun getExtras(): IntentContent? = intent.extras?.getString("hostname")?.let { hostname ->
        intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
            IntentContent(hostname, dataAccount)
        }
    }

    override fun backwardFromTransfer() {
        finish()
    }
}