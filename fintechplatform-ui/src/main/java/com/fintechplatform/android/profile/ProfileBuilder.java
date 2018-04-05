package com.fintechplatform.android.profile;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.address.AddressUIComponent;
import com.fintechplatform.android.profile.address.DaggerAddressUIComponent;
import com.fintechplatform.android.profile.address.ui.AddressUIModule;
import com.fintechplatform.android.profile.contacts.ContactsUIComponent;
import com.fintechplatform.android.profile.contacts.DaggerContactsUIComponent;
import com.fintechplatform.android.profile.contacts.ui.ContactsUIModule;
import com.fintechplatform.android.profile.idcards.DaggerIdentityCardsUIComponent;
import com.fintechplatform.android.profile.idcards.IdentityCardsUIComponent;
import com.fintechplatform.android.profile.idcards.ui.IdentityCardsUIModule;
import com.fintechplatform.android.profile.jobinfo.DaggerJobInfoUIComponent;
import com.fintechplatform.android.profile.jobinfo.JobInfoUIComponent;
import com.fintechplatform.android.profile.jobinfo.ui.JobInfoUIModule;
import com.fintechplatform.android.profile.lightdata.DaggerLightDataUIComponent;
import com.fintechplatform.android.profile.lightdata.LightDataUIComponent;
import com.fintechplatform.android.profile.lightdata.ui.LightDataUIModule;

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
