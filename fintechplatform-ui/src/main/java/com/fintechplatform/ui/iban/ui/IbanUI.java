package com.fintechplatform.ui.iban.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class IbanUI implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    protected IbanUI() {}

    public IbanUI(String hostname, DataAccount configuration, Context context) {
        DaggerIBANViewComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostname)))
                .iBANPresenterModule(new IBANPresenterModule(configuration))
                .ibanAPIModule(new IbanAPIModule(hostname))
                .profileAPIModule(new ProfileAPIModule(hostname))
                .enterpriseAPIModule(new EnterpriseAPIModule(hostname))
                .build()
                .inject(this);
    }
/*
    public static IBANViewComponent createIBANViewComponent(Context context, IBANContract.View view) {
        return instance.buildIbanViewComponent(context, view);
    }

    protected IBANViewComponent buildIbanViewComponent(Context context, IBANContract.View view) {
        return DaggerIBANViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostname))
                .iBANPresenterModule(new IBANPresenterModule(view, instance.configuration))
                .ibanAPIModule(new IbanAPIModule(instance.hostname))
                .profileAPIModule(new ProfileAPIModule(instance.hostname))
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostname))
                .build();
    }
*/
    public void start(Context context){
        Intent intent = new Intent(context, IBANActivity.class);
        context.startActivity(intent);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
