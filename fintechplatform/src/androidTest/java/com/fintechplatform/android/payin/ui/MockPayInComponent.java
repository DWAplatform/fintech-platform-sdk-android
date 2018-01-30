package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.alert.MockAlertHelpersModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {MockPayInPresenterModule.class, MockAlertHelpersModule.class, Secure3DUIModule.class})
public interface MockPayInComponent extends PayInViewComponent {
    void inject(PayInActivityTest activityTest);
}
