package com.fintechplatform.ui.cashin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.FintechPlatform
import com.fintechplatform.ui.R
import com.fintechplatform.ui.card.ui.PaymentCardContract
import com.fintechplatform.ui.card.ui.PaymentCardFragment
import com.fintechplatform.ui.cashin.di.CashInFragment
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.secure3d.ui.Secure3DContract
import com.fintechplatform.ui.secure3d.ui.Secure3DFragment

class CashInActivity : AppCompatActivity(), 
        CashInContract.Navigation,
        PaymentCardContract.Navigation,
        Secure3DContract.Navigation {

    private data class IntentContent(val hostName: String,
                             val dataAccount: DataAccount,
                             val isSandbox: Boolean)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var frag : CashInFragment ?= null

        val initialAmount =
                if (intent.hasExtra("initialAmount"))
                    intent.getLongExtra("initialAmount", 0L)
                else null

        savedInstanceState?.let{ extras ->
            frag = supportFragmentManager.findFragmentByTag(CashInFragment.toString()) as CashInFragment
        }

        getExtras()?.let {intent ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, CashInFragment.toString())
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, CashInFragment.newInstance(intent.hostName, intent.dataAccount, intent.isSandbox, initialAmount), CashInFragment.toString())
                    .commit()
        }

        setContentView(R.layout.activity_with_fragment)
    }

    private fun getExtras(): IntentContent? = intent.extras?.getString("hostname")?.let { hostname ->
        intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
            intent.extras?.getBoolean("isSandbox")?.let { isSandbox ->
                IntentContent(hostname, dataAccount, isSandbox)
            }
        }
    }

    override fun goToPaymentCard() {
        getExtras()?.let {
            val frag = FintechPlatform.buildPaymentCardUI(it.hostName, it.dataAccount, it.isSandbox).createFragment(it.hostName, it.dataAccount, it.isSandbox)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.contentContainer, frag, PaymentCardFragment.toString())
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun goBackwardFromPaymentCard() {
        supportFragmentManager.popBackStack()
        val fragWithtag = supportFragmentManager.findFragmentByTag(PaymentCardFragment.toString())
        supportFragmentManager.beginTransaction().remove(fragWithtag).commit()
    }

    override fun goTo3dSecure(redirecturl: String) {
        val frag = FintechPlatform.build3DSecureUI().createFragment(redirecturl)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentContainer, frag, Secure3DFragment.toString())
                .addToBackStack(null)
                .commit()
    }

    override fun goBackwardFromCashIn() {
        finish()
    }

    override fun goBackwardFrom3dSecure() {
        val fragWithtag = supportFragmentManager.findFragmentByTag(Secure3DFragment.toString())
        supportFragmentManager
                .beginTransaction()
                .remove(fragWithtag)
                .commit()
        supportFragmentManager.popBackStack()
    }
}

