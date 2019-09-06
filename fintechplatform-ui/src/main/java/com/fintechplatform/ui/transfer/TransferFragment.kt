package com.fintechplatform.ui.transfer

import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.MoneyValueInputFilter
import com.fintechplatform.ui.transfer.di.TransferViewComponent
import kotlinx.android.synthetic.main.activity_transfer_p2p.*
import kotlinx.android.synthetic.main.activity_transfer_p2p.view.*
import javax.inject.Inject


open class TransferFragment: Fragment(), TransferContract.View {
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var imageHelper: ImageHelper
    @Inject lateinit var presenter: TransferContract.Presenter
    
    var navigation: TransferContract.Navigation? = null

    open fun createTransferViewComponent(context: Context, view: TransferContract.View, hostName: String, dataAccount: DataAccount): TransferViewComponent {
        return TransferUI.Builder.buildTransferViewComponent(context, view, hostName, dataAccount)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_transfer_p2p, container, false)

        arguments.getString("hostname")?.let { hostname ->
            arguments.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createTransferViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }
        
        view.amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        view.amountText.filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        view.forwardButton.setOnClickListener { presenter.onConfirm() }
        view.backwardButton.setOnClickListener { presenter.onAbortClick() }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.getParcelable<DataAccount>("transferTo")?.let {
            presenter.initialize(it.ownerId, it.accountId, it.tenantId, it.accountType)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? TransferContract.Navigation)?.let { 
            navigation = it
        }?: Log.e(TransferFragment::class.java.canonicalName, "TransferContract.Navigation interface must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }
    
    override fun enableForwardButton(isEnable: Boolean) {
        forwardButton.isEnabled = isEnable
    }

    override fun getAmountText(): String {
        return amountText.text.toString()
    }

    override fun getMessageText(): String {
        return messageText.text.toString()
    }

    override fun setBalanceAmountText(amount: String) {
        newBalanceAmountLabel.text = amount
    }

    override fun setPersonFullName(fullname: String) {
        person_fullname.text = fullname
    }

    override fun setPositiveBalanceColorText() {
        newBalanceAmountLabel.setTextColor(Color.BLACK)
    }

    override fun setNegativeBalanceColorText() {
        newBalanceAmountLabel.setTextColor(Color.RED)
    }

    override fun setFeeAmountText(fee: String) {
        feeAmountLabel.text = fee
    }

    override fun showCommunicationWait() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(context).show()
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(context).show()
    }

    override fun playSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context, notification)
        r.play()
    }

    override fun showSuccessDialog() {
        alertHelpers.successGeneric(context, "Transazione avvenuta con successo!") { _,_ ->
            goBack()
        }
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(context) { _,_ ->
            navigation?.backwardFromTransfer()
        }
    }

    override fun goBack() {
        navigation?.backwardFromTransfer()
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    companion object {
        fun newInstance(hostName: String, dataAccount: DataAccount, transferTo: DataAccount): TransferFragment {
            val frag = TransferFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            args.putParcelable("transferTo", transferTo)
            frag.arguments = args
            return frag
        }
    }
}