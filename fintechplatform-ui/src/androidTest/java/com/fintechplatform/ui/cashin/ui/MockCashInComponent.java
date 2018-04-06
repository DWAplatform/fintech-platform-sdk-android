package com.fintechplatform.ui.cashin.ui;

import com.fintechplatform.ui.alert.MockAlertHelpersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {MockPayInPresenterModule.class, MockAlertHelpersModule.class, Secure3DUIHelperModule.class})
public interface MockCashInComponent extends CashInViewComponent {
    void inject(CashInActivityTest activityTest);
}
