package com.fintechplatform.ui.enterprise.contacts.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.models.DataAccount;

public class EnterpriseContactsUI {
    private String hostName;
    private DataAccount configuration;

    static EnterpriseContactsUI instance;

    public EnterpriseContactsUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    private EnterpriseContactsViewComponent buildContactsViewComponet(Context context, EnterpriseContactsContract.View view) {
        return DaggerEnterpriseContactsViewComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), instance.hostName)))
                .enterpriseContactsPresenterModule(new EnterpriseContactsPresenterModule(view, instance.configuration))
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostName))
                .build();
    }
    public EnterpriseContactsViewComponent createContactsComponent(Context context, EnterpriseContactsContract.View view) {
        return instance.buildContactsViewComponet(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, EnterpriseContactsActivity.class);
        context.startActivity(intent);
    }
}
