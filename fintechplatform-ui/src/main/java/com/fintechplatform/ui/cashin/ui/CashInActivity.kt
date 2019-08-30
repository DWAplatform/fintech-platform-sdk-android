package com.fintechplatform.ui.cashin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.FintechPlatform
import com.fintechplatform.ui.R
import com.fintechplatform.ui.cashin.ui.CashInFragment
import com.fintechplatform.ui.models.DataAccount

class CashInActivity : AppCompatActivity(), CashInContract.Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialAmount =
                if (intent.hasExtra("initialAmount"))
                    intent.getLongExtra("initialAmount", 0L)
                else null
        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                intent.extras?.getBoolean("isSandbox")?.let { isSandbox ->
                    val frag = CashInFragment.newInstance(hostname, dataAccount, isSandbox, initialAmount)
                    supportFragmentManager.beginTransaction()
                            .add(R.id.contentContainer, frag)
                            .commit()
                }
            }
        }

        setContentView(R.layout.activity_with_fragment)
    }

    override fun goToPaymentCard() {
        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                intent.extras?.getBoolean("isSandbox")?.let { isSandbox ->
                    FintechPlatform.buildPaymentCardUI(hostname, dataAccount, isSandbox).start(this)
                }
            }
        }

    }

    override fun goTo3dSecure(redirecturl: String) {
        FintechPlatform.build3DSecureUI().start(this, redirecturl)
    }
}

