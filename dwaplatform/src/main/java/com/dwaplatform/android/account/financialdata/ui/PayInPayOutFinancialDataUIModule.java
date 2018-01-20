package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;

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
