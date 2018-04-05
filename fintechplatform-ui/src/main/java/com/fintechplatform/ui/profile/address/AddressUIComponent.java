package com.fintechplatform.ui.profile.address;

import com.fintechplatform.ui.profile.address.ui.AddressUI;
import com.fintechplatform.ui.profile.address.ui.AddressUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        AddressUIModule.class
})
public interface AddressUIComponent {
    AddressUI getAddressUI();
}
