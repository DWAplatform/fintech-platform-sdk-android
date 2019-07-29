package com.fintechplatform.ui.qrtransfer.creditui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.fragment_qr_receive_show.*
import javax.inject.Inject

/**
 * Shows the qrcode receive
 */
class QrReceiveShowFragment: androidx.fragment.app.Fragment(), QrReceiveShowContract.View {

    private var transactionId: String? = null
    @Inject lateinit var presenter : QrReceiveShowContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionId = arguments?.let {
            arguments?.getString(TRANSACTION_ID_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qr_receive_show, container, false)
        QrCreditTransferUI.instance.buildQrShowComponent(activity,this).inject(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.generateQrCode(transactionId)
    }

    companion object {
        private val TRANSACTION_ID_PARAM = "transactionId"

        fun newInstance(transactionId: String): QrReceiveShowFragment {
            val fragment = QrReceiveShowFragment()
            val args = Bundle()
            args.putString(TRANSACTION_ID_PARAM, transactionId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setQrImage(bmp: Bitmap) {
        img_result_qr.setImageBitmap(bmp)
    }
}