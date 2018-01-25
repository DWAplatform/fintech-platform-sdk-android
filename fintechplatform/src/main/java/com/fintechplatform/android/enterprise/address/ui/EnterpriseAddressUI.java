package com.fintechplatform.android.enterprise.address.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.models.DataAccount;

public class EnterpriseAddressUI {

    private String hostName;
    private DataAccount configuration;

    protected static EnterpriseAddressUI instance;

    public EnterpriseAddressUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    protected EnterpriseAddressViewComponent buildAddressComponent(Context context, EnterpriseAddressContract.View view) {
        return DaggerEnterpriseAddressViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context),instance.hostName))
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostName))
                .enterpriseAddressPresenterModule(new EnterpriseAddressPresenterModule(view, instance.configuration))
                .build();

    }

    public EnterpriseAddressViewComponent createAddressComponent(Context context, EnterpriseAddressContract.View view){
        return instance.buildAddressComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent( context, EnterpriseAddressActivity.class);
        context.startActivity(intent);
    }
}
