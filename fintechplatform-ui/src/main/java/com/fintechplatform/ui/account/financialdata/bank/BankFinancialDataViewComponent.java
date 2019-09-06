package com.fintechplatform.ui.account.financialdata.bank;

import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        BankFinancialDataPresenterModule.class,
        PaymentCardPersistanceModule.class,
        IbanPersistanceDBModule.class,
        AlertHelpersModule.class,
        NetModule.class,
        PaymentCardAPIModule.class,
        IbanAPIModule.class,
        LogModule.class
})
public interface BankFinancialDataViewComponent {
    void inject(BankFinancialDataActivity activity);
}
