package com.fintechplatform.ui.enterprise.address;

import com.fintechplatform.ui.enterprise.address.ui.EnterpriseAddressUI;
import com.fintechplatform.ui.enterprise.address.ui.EnterpriseAddressUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseAddressUIModule.class
})
public interface EnterpriseAddressUIComponent {
    EnterpriseAddressUI getEnterpriseAddressUI();
}
