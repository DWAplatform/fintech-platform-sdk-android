package com.fintechplatform.ui.iban.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.FintechPlatform;

public class IbanUI {

    protected IbanUI() {
    }

    public static IBANContract.Presenter getIbanPresenter(Context context) {
        return DaggerIBANPrensenterComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), FintechPlatform.hostName)))
                .iBANPresenterModule(new IBANPresenterModule(FintechPlatform.dataAccount))
                .ibanAPIModule(new IbanAPIModule(FintechPlatform.hostName))
                .profileAPIModule(new ProfileAPIModule(FintechPlatform.hostName))
                .enterpriseAPIModule(new EnterpriseAPIModule(FintechPlatform.hostName))
                .build().getPresenter();
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

}
