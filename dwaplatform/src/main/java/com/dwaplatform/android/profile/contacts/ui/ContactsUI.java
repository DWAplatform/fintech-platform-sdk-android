package com.dwaplatform.android.profile.contacts.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPIModule;

public class ContactsUI {
    private String hostName;
    private DataAccount configuration;

    static ContactsUI instance;

    public ContactsUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    private ContactsViewComponent buildContactsViewComponet(Context context, ContactsContract.View view) {
        return DaggerContactsViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .keyChainModule(new KeyChainModule(context))
                .contactsPresenterModule(new ContactsPresenterModule(view, instance.configuration))
                .profileAPIModule(new ProfileAPIModule(instance.hostName))
                .build();
    }
    public ContactsViewComponent createContactsComponent(Context context, ContactsContract.View view) {
        return instance.buildContactsViewComponet(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, ContactsActivity.class);
        context.startActivity(intent);
    }
}
