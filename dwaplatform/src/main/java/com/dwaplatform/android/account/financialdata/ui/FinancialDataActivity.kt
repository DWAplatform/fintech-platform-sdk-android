package com.dwaplatform.android.account.financialdata.ui

import android.os.Bundle
import android.view.View
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataActivity
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataContract
import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI
import com.raizlabs.android.dbflow.kotlinextensions.insert
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