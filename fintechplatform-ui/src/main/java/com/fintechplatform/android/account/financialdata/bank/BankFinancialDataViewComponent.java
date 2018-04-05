package com.fintechplatform.android.account.financialdata.bank;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.card.db.PaymentCardPersistanceModule;
import com.fintechplatform.android.iban.api.IbanAPIModule;
import com.fintechplatform.android.iban.db.IbanPersistanceDBModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payout.ui.IbanUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        BankFinancialDataPresenterModule.class,
        PaymentCardPersistanceModule.class,
        IbanPersistanceDBModule.class,
        AlertHelpersModule.class,
        PaymentCardUIModule.class,
        IbanUIModule.class,
        NetModule.class,
        PaymentCardAPIModule.class,
        IbanAPIModule.class,
        LogModule.class
})
public interface BankFinancialDataViewComponent {
    void inject(BankFinancialDataActivity activity);
}
