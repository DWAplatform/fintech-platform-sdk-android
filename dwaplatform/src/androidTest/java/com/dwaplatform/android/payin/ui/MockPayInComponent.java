package com.dwaplatform.android.payin.ui;

import com.dwaplatform.android.alert.MockAlertHelpersModule;
import com.dwaplatform.android.payin.PayInUIComponent;
import com.dwaplatform.android.secure3d.ui.Secure3DUIModule;

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
