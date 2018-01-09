package com.dwaplatform.android.profile;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.contacts.ContactsUIComponent;
import com.dwaplatform.android.profile.contacts.DaggerContactsUIComponent;
import com.dwaplatform.android.profile.contacts.ui.ContactsUIModule;
import com.dwaplatform.android.profile.lightdata.DaggerLightDataUIComponent;
import com.dwaplatform.android.profile.lightdata.LightDataUIComponent;
import com.dwaplatform.android.profile.lightdata.ui.LightDataUIModule;

public class ProfileBuilder {

    public LightDataUIComponent createLightDataUI(String hostname, DataAccount dataAccount ) {
        return DaggerLightDataUIComponent.builder()
                .lightDataUIModule(new LightDataUIModule(hostname, dataAccount))
                .build();
    }

    public ContactsUIComponent createContactsUI(String hostname, DataAccount dataAccount ) {
        return DaggerContactsUIComponent.builder()
                .contactsUIModule(new ContactsUIModule(hostname, dataAccount))
                .build();
    }
}
