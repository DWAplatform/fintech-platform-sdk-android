package com.fintechplatform.ui.payin.ui;

import com.fintechplatform.ui.alert.MockAlertHelpersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {MockPayInPresenterModule.class, MockAlertHelpersModule.class, Secure3DUIHelperModule.class})
public interface MockPayInComponent extends PayInViewComponent {
    void inject(PayInActivityTest activityTest);
}
