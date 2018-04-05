package com.fintechplatform.api.payin.ui;

import com.fintechplatform.api.alert.MockAlertHelpersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {MockPayInPresenterModule.class, MockAlertHelpersModule.class, Secure3DUIHelperModule.class})
public interface MockPayInComponent extends PayInViewComponent {
    void inject(PayInActivityTest activityTest);
}
