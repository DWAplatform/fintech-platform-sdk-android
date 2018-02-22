package com.fintechplatform.android.account.payinpayoutfinancialdata.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.fintechplatform.android.R
import com.fintechplatform.android.alert.AlertHelpers
import com.fintechplatform.android.card.ui.PaymentCardUI
import com.fintechplatform.android.iban.ui.IbanUI
import kotlinx.android.synthetic.main.activity_financialdata.*
import javax.inject.Inject


/**
 * Menu for Credit card and IBAN CRUD operations
 */
open class PayInPayOutFinancialDataActivity : FragmentActivity() , PayInPayOutFinancialDataContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: PayInPayOutFinancialDataContract.Presenter
    @Inject lateinit var bankAccountUI: IbanUI
    @Inject lateinit var paymentCardUI: PaymentCardUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financialdata)
        PayInPayOutFinancialDataUI.instance.buildFinancialDataViewComponent(this, this).inject(this)

        presenter.initFinancialData()

        cardcontainer.setOnClickListener {
            alertHelpers.confirmCancelGeneric(this,
                    "Registrazione Carta Debito/Credito",
                    "Affinchè la carta registrata sia valida, " +
                            "è necessario effettuare una ricarica entro 30 minuti massimo. " +
                            "Si vuole continuare?",
                    { d, i ->
                        paymentCardUI.start(this)
                    }, { d, i -> }).show()
        }

        ibancontainer.setOnClickListener {
            bankAccountUI.start(this)
        }

        backwardButton.setOnClickListener { presenter.onBackwardClicked() }

    }

    override fun enableIBAN(isEnable: Boolean) {
        ibancontainer.isEnabled = isEnable
        ibanDetailarrow.visibility = View.GONE
    }

    override fun enablePaymentCard(isEnable: Boolean) {
        cardcontainer.isEnabled = isEnable
        cardDetailarrow.visibility = View.GONE
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
        cardValueText.text = presenter.calcCardValue() ?: ""
        ibanText.text = presenter.calcIBANValue() ?: ""
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this) { _,_ ->
            finish()
        }
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(this)
    }

    override fun setPaymentCardNumber(cardNumber: String) {
        cardValueText.text = cardNumber
    }

    override fun setBankAccountText(iban: String) {
        ibanText.text = iban
    }
}
