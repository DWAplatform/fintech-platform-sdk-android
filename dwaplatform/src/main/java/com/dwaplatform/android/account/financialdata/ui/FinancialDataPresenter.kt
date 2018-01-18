package com.dwaplatform.android.account.financialdata.ui

import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.iban.db.IbanPersistanceDB
import com.dwaplatform.android.models.DataAccount
import javax.inject.Inject

/**
 * Created by ingrid on 18/01/18.
 */
class FinancialDataPresenter @Inject constructor(val view: FinancialDataContract.View,
                                                 //val configuration: DataAccount,
                                                 val ibanDB: IbanPersistanceDB,
                                                 val cardDB: PaymentCardPersistenceDB): FinancialDataContract.Presenter {
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