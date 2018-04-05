package com.fintechplatform.android.profile.address;

import com.fintechplatform.android.profile.address.ui.AddressUI;
import com.fintechplatform.android.profile.address.ui.AddressUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        AddressUIModule.class
})
public interface AddressUIComponent {
    AddressUI getAddressUI();
}
