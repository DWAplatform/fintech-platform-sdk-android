package com.fintechplatform.ui.profile;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.profile.address.AddressUIComponent;
import com.fintechplatform.ui.profile.address.DaggerAddressUIComponent;
import com.fintechplatform.ui.profile.address.ui.AddressUIModule;
import com.fintechplatform.ui.profile.contacts.ContactsUIComponent;
import com.fintechplatform.ui.profile.contacts.DaggerContactsUIComponent;
import com.fintechplatform.ui.profile.contacts.ui.ContactsUIModule;
import com.fintechplatform.ui.profile.idcards.DaggerIdentityCardsUIComponent;
import com.fintechplatform.ui.profile.idcards.IdentityCardsUIComponent;
import com.fintechplatform.ui.profile.idcards.ui.IdentityCardsUIModule;
import com.fintechplatform.ui.profile.jobinfo.DaggerJobInfoUIComponent;
import com.fintechplatform.ui.profile.jobinfo.JobInfoUIComponent;
import com.fintechplatform.ui.profile.jobinfo.ui.JobInfoUIModule;
import com.fintechplatform.ui.profile.lightdata.DaggerLightDataUIComponent;
import com.fintechplatform.ui.profile.lightdata.LightDataUIComponent;
import com.fintechplatform.ui.profile.lightdata.ui.LightDataUIModule;

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
