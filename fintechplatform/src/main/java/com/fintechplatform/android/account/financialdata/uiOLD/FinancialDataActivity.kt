package com.fintechplatform.android.account.financialdata.uiOLD

import android.os.Bundle
import android.view.View
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataActivity
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataContract
import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI
import kotlinx.android.synthetic.main.activity_financialdata.*
import javax.inject.Inject

class FinancialDataActivity : PayInPayOutFinancialDataActivity() , PayInPayOutFinancialDataContract.View {

    @Inject lateinit var payinpayoutui: PayInPayOutFinancialDataUI

    override fun onCreate(savedInstanceState: Bundle?) {

        FinancialDataUI.instance.buildFinancialDataViewComponent(this, this).inject(this)
        super.onCreate(savedInstanceState)

        ibancontainer.isEnabled = false
        ibanDetailarrow.visibility = View.GONE

        cardcontainer.isEnabled = false
        cardDetailarrow.visibility = View.GONE
    }
}