package com.dwaplatform.android.profile.address.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPIModule;

public class AddressUI {

    private String hostName;
    private DataAccount configuration;

    protected static AddressUI instance;

    public AddressUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    protected AddressViewComponent buildAddressComponent(Context context, AddressContract.View view) {
        return DaggerAddressViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context),instance.hostName))
                .profileAPIModule(new ProfileAPIModule(instance.hostName))
                .addressPresenterModule(new AddressPresenterModule(view, instance.configuration))
                .build();

    }

    public AddressViewComponent createAddressComponent(Context context, AddressContract.View view){
        return instance.buildAddressComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent( context, AddressActivity.class);
        context.startActivity(intent);
    }
}
