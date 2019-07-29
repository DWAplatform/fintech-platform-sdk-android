package com.fintechplatform.ui.qrtransfer.creditui

import android.os.Bundle
import com.fintechplatform.ui.R
import javax.inject.Inject

/**
 * Manage Qrcode Receive feature, with the help of qrreceive amount and show fragments
 */
class QrReceiveActivity : androidx.fragment.app.FragmentActivity(), QrReceiveActivityContract.View {

    @Inject lateinit var presenter: QrReceiveActivityContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_transfer)

        QrCreditTransferUI.instance.buildQrComponent(this, this).inject(this)
        presenter.createAmountFragment()

    }

    override fun showShowFragmentWithTransactionId(transactionid: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, QrReceiveShowFragment.newInstance(transactionid))
                .commit()
    }

    override fun showAmountFragment() {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, QrReceiveAmountFragment.newInstance())
                .commit()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun goBack() {
        finish()
    }
}
