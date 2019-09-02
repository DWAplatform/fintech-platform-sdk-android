package com.fintechplatform.ui.card.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount

/**
 * UI for Credit card number, data, ccv
 */
class PaymentCardActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                intent.extras?.getBoolean("isSandbox")?.let {isSandbox ->
                    val frag = PaymentCardFragment.newInstance(hostname, dataAccount, isSandbox)
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.contentContainer, frag)
                            .commit()
                }
            }
        }

        setContentView(R.layout.activity_with_fragment)
    }
}