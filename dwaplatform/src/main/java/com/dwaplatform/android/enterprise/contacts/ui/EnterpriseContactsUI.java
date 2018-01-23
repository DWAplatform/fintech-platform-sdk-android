package com.dwaplatform.android.enterprise.contacts.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.contacts.ui.ContactsActivity;

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
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
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
