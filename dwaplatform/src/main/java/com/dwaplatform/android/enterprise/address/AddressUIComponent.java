package com.dwaplatform.android.enterprise.address;

import com.dwaplatform.android.enterprise.address.ui.EnterpriseAddressUI;
import com.dwaplatform.android.enterprise.address.ui.EnterpriseAddressUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseAddressUIModule.class
})
public interface AddressUIComponent {
    EnterpriseAddressUI getAddressUI();
}
