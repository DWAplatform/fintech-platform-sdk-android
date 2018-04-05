package com.fintechplatform.ui.account.financialdata.bank

import com.fintechplatform.ui.account.financialdata.payinpayout.FinancialDataActivity


class BankFinancialDataActivity : FinancialDataActivity() {
    override fun injectAll(){
        BankFinancialDataUI.instance?.buildFinancialDataViewComponent(this, this)?.inject(this)
    }
}