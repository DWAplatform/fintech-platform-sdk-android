package com.dwaplatform.android.account.financialdata.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.dwaplatform.android.R
import com.dwaplatform.android.alert.AlertHelpers
import com.dwaplatform.android.card.ui.PaymentCardUI
import com.dwaplatform.android.iban.ui.IbanUI
import kotlinx.android.synthetic.main.activity_financialdata.*
import javax.inject.Inject


/**
 * Menu for Credit card and IBAN CRUD operations
 */
class FinancialDataActivity: FragmentActivity() , FinancialDataContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: FinancialDataContract.Presenter
    @Inject lateinit var bankAccountUI: IbanUI
    @Inject lateinit var paymentCardUI: PaymentCardUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financialdata)
        FinancialDataUI.instance.buildFinancialDataViewComponent(this).inject(this)
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

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun onResume() {
        super.onResume()
        cardValueText.text = presenter.calcCardValue() ?: ""
        ibanText.text = presenter.calcIBANValue() ?: ""
    }
}
