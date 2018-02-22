package com.fintechplatform.android.account.financialdata.uiOLD;

import com.fintechplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayInPayOutFinancialDataUIModule {

    private PayInPayOutFinancialDataUI payInPayOutFinancialData;

    public PayInPayOutFinancialDataUIModule(PayInPayOutFinancialDataUI payInPayOutFinancialData) {
        this.payInPayOutFinancialData = payInPayOutFinancialData;
    }

    @Provides
    @Singleton
    PayInPayOutFinancialDataUI providesPayInPayOutFinancialDataUI() {
        return payInPayOutFinancialData;
    }
}
