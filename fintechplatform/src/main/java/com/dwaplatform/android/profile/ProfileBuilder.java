package com.dwaplatform.android.profile;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.address.AddressUIComponent;
import com.dwaplatform.android.profile.address.DaggerAddressUIComponent;
import com.dwaplatform.android.profile.address.ui.AddressUIModule;
import com.dwaplatform.android.profile.contacts.ContactsUIComponent;
import com.dwaplatform.android.profile.contacts.DaggerContactsUIComponent;
import com.dwaplatform.android.profile.contacts.ui.ContactsUIModule;
import com.dwaplatform.android.profile.idcards.DaggerIdentityCardsUIComponent;
import com.dwaplatform.android.profile.idcards.IdentityCardsUIComponent;
import com.dwaplatform.android.profile.idcards.ui.IdentityCardsUIModule;
import com.dwaplatform.android.profile.jobinfo.DaggerJobInfoUIComponent;
import com.dwaplatform.android.profile.jobinfo.JobInfoUIComponent;
import com.dwaplatform.android.profile.jobinfo.ui.JobInfoUIModule;
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

    public AddressUIComponent createAddressUI(String hostname, DataAccount dataAccount ) {
        return DaggerAddressUIComponent.builder()
                .addressUIModule(new AddressUIModule(hostname, dataAccount))
                .build();
    }

    public JobInfoUIComponent createJobInfoUI(String hostname, DataAccount dataAccount ) {
        return DaggerJobInfoUIComponent.builder()
                .jobInfoUIModule(new JobInfoUIModule(hostname, dataAccount))
                .build();
    }

    public IdentityCardsUIComponent createIdCardsUI(String hostname, DataAccount dataAccount) {
        return DaggerIdentityCardsUIComponent.builder()
                .identityCardsUIModule(new IdentityCardsUIModule(hostname, dataAccount))
                .build();
    }
}
