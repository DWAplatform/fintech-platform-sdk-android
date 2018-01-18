package com.dwaplatform.android.account.payinpayoutfinancialdata.ui

import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.iban.db.IbanPersistanceDB
import javax.inject.Inject

/**
 * Created by ingrid on 18/01/18.
 */
class PayInPayOutFinancialDataPresenter @Inject constructor(val view: PayInPayOutFinancialDataContract.View,
                                                 //val configuration: DataAccount,
                                                            val ibanDB: IbanPersistanceDB,
                                                            val cardDB: PaymentCardPersistenceDB): PayInPayOutFinancialDataContract.Presenter {
    override fun onBackwardClicked(){
        view.goBack()
    }

    override fun calcCardValue(): String? {
        val optcc = cardDB.load()
        return optcc?.let { cc ->
            "${cc.alias} ${cc.expiration}"
        }
    }

    override fun calcIBANValue(): String? {
        val optiban = ibanDB.load()
        return optiban?.iban
    }
}