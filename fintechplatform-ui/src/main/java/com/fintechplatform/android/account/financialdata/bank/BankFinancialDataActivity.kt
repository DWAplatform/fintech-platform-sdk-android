package com.fintechplatform.android.account.financialdata.bank

import com.fintechplatform.android.account.financialdata.payinpayout.FinancialDataActivity


class BankFinancialDataActivity : FinancialDataActivity() {
    override fun injectAll(){
        BankFinancialDataUI.instance?.buildFinancialDataViewComponent(this, this)?.inject(this)
    }
}