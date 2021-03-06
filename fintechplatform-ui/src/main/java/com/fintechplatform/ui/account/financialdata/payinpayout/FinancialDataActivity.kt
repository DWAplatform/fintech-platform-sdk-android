package com.fintechplatform.ui.account.financialdata.payinpayout

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_financialdata.*
import javax.inject.Inject


/**
 * Menu for Credit card and IBAN CRUD operations
 */
open class FinancialDataActivity : FragmentActivity() , FinancialDataContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: FinancialDataContract.Presenter

    open fun injectAll() {
        PayInPayOutFinancialDataUI.instance?.buildFinancialDataViewComponent(this, this)?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_financialdata)

        injectAll()

        presenter.initialize()
        presenter.initFinancialData()

        cardcontainer.setOnClickListener {
            alertHelpers.confirmCancelGeneric(this,
                    "Registrazione Carta Debito/Credito",
                    "Affinchè la carta registrata sia valida, " +
                            "è necessario effettuare una ricarica entro 30 minuti massimo. " +
                            "Si vuole continuare?",
                    { d, i ->
                       //todo  paymentCardUI.start(this)
                    }, { d, i -> }).show()
        }

        ibancontainer.setOnClickListener {
            // TODO fix hostname dataAccount
            //bankAccountUI.startActivity(this, hostname, dataAccount)
        }

        backwardButton.setOnClickListener { presenter.onBackwardClicked() }

    }

    override fun enableIBAN(isEnable: Boolean) {
        ibancontainer.isEnabled = isEnable
    }

    override fun enablePaymentCard(isEnable: Boolean) {
        cardcontainer.isEnabled = isEnable

    }

    override fun showGoToEditArrow() {
        cardDetailarrow.visibility = View.VISIBLE
        ibanDetailarrow.visibility = View.VISIBLE
    }

    override fun hideGoToEditArrow() {
        cardDetailarrow.visibility = View.GONE
        ibanDetailarrow.visibility = View.GONE
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
//        cardValueText.text = presenter.calcCardValue() ?: ""
//        ibanText.text = presenter.calcIBANValue() ?: ""
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
