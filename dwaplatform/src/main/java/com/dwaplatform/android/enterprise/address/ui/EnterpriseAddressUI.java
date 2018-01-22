package com.dwaplatform.android.enterprise.address.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.address.ui.AddressActivity;
import com.dwaplatform.android.profile.address.ui.AddressContract;
import com.dwaplatform.android.profile.address.ui.DaggerAddressViewComponent;
import com.dwaplatform.android.profile.api.ProfileAPIModule;

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
        Intent intent = new Intent( context, AddressActivity.class);
        context.startActivity(intent);
    }
}
