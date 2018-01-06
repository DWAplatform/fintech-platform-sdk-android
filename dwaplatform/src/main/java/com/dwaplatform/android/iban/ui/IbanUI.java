package com.dwaplatform.android.iban.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.iban.api.IbanAPIModule;
import com.dwaplatform.android.models.DataAccount;

public class IbanUI {

    private String hostname;
    private DataAccount configuration;

    protected static IbanUI instance;

    protected IbanUI() {}

    public IbanUI(String hostname, DataAccount configuration) {
        this.hostname = hostname;
        this.configuration = configuration;
    }

    public static IBANViewComponent createIBANViewComponent(Context context, IBANContract.View view) {
        return instance.buildIbanViewComponent(context, view);
    }

    protected IBANViewComponent buildIbanViewComponent(Context context, IBANContract.View view) {
        return DaggerIBANViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .iBANPresenterModule(new IBANPresenterModule(view, instance.configuration))
                .ibanAPIModule(new IbanAPIModule(instance.hostname))
                .keyChainModule(new KeyChainModule(context))
                .build();
    }

    public void start(Context context){
        instance = this;
        Intent intent = new Intent(context, IBANActivity.class);
        context.startActivity(intent);
    }
}
