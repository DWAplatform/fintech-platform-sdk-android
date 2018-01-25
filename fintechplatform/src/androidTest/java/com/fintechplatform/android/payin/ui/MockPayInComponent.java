package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.alert.MockAlertHelpersModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 12/12/17.
 */
@Singleton
@Component(modules= {MockPayInPresenterModule.class, MockAlertHelpersModule.class, Secure3DUIModule.class})
public interface MockPayInComponent extends PayInViewComponent {
    void inject(PayInActivityTest activityTest);
}
