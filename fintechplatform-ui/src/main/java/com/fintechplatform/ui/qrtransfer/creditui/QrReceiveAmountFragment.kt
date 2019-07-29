package com.fintechplatform.ui.qrtransfer.creditui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import kotlinx.android.synthetic.main.fragment_qr_receive_amount.*
import javax.inject.Inject

/**
 * Requests to the user the amount to create a qrcode receive
 */
class QrReceiveAmountFragment: Fragment(), QrReceiveAmountContract.View {

    @Inject lateinit var presenter: QrReceiveAmountContract.Presenter
    @Inject lateinit var alertHelpers: AlertHelpers
    var onAttachDelegate: QrReceiveAmountFragmentPresenter.QrReceiveFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_qr_receive_amount, container, false)
        QrCreditTransferUI.instance.buildQrAmountComponent(activity, this).inject(this)
        onAttachDelegate?.let { delegate ->
            presenter.setListener(delegate)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.refreshConfirmButton()
        amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        forwardButton.setOnClickListener { presenter.onConfirm() }

        backwardButton.setOnClickListener { presenter.onAbortClick() }
    }

    override fun getAmountTextLength(): Int {
        return amountText.length()
    }

    override fun getAmountText(): String {
        return amountText.text.toString()
    }

    override fun getMessagetext(): String? {
        if(messageText.text.toString().isNullOrBlank()) {
            return null
        }
        else {
            return messageText.text.toString()
        }
    }

    override fun setForwardButtonEnable(isEnabled: Boolean) {
        forwardButton.isEnabled = isEnabled
    }

    override fun showCommunicationWait() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        context?.let {
            alertHelpers.internalError(it).show()
        }
    }

    override fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun showTokenExpiredWarning() {
        context?.let {
            alertHelpers.tokenExpired(it) { _,_ ->
                activity?.finish()
            }
        }
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            val keyboard =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val activityRef = context as QrReceiveActivity

        if (activityRef.presenter is QrReceiveAmountFragmentPresenter.QrReceiveFragmentListener) {
            onAttachDelegate = activityRef.presenter as QrReceiveAmountFragmentPresenter.QrReceiveFragmentListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.removeListener()
    }

    companion object {
        fun newInstance(): QrReceiveAmountFragment {
            return QrReceiveAmountFragment()
        }
    }
}