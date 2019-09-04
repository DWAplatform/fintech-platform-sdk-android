package com.fintechplatform.ui.account.financialdata.payinpayout;

import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.ui.payout.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayInPayOutFinancialDataPresenterModule.class,
        PaymentCardPersistanceModule.class,
        IbanPersistanceDBModule.class,
        AlertHelpersModule.class,
        IbanUIModule.class,
        NetModule.class,
        PaymentCardAPIModule.class,
        IbanAPIModule.class,
        PayInPayOutFinancialDataUIModule.class,
        LogModule.class
})
public interface PayInPayOutFinancialDataViewComponent {
    void inject(FinancialDataActivity activity);
}
