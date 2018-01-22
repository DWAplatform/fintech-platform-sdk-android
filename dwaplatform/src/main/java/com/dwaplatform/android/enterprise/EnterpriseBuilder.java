package com.dwaplatform.android.enterprise;


import com.dwaplatform.android.enterprise.address.DaggerEnterpriseAddressUIComponent;
import com.dwaplatform.android.enterprise.address.EnterpriseAddressUIComponent;
import com.dwaplatform.android.enterprise.address.ui.EnterpriseAddressUIModule;
import com.dwaplatform.android.enterprise.contacts.DaggerEnterpriseContactsUIComponent;
import com.dwaplatform.android.enterprise.contacts.EnterpriseContactsUIComponent;
import com.dwaplatform.android.enterprise.contacts.ui.EnterpriseContactsUIModule;
import com.dwaplatform.android.enterprise.info.DaggerEnterpriseInfoUIComponent;
import com.dwaplatform.android.enterprise.info.EnterpriseInfoUIComponent;
import com.dwaplatform.android.enterprise.info.ui.EnterpriseInfoUIModule;
import com.dwaplatform.android.models.DataAccount;

public class EnterpriseBuilder {

    public EnterpriseInfoUIComponent buildEnterpriseInfoUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseInfoUIComponent.builder()
                .enterpriseInfoUIModule(new EnterpriseInfoUIModule(hostName, configuration))
                .build();
    }

    public EnterpriseContactsUIComponent buildEnterpriseContactsUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseContactsUIComponent.builder()
                .enterpriseContactsUIModule(new EnterpriseContactsUIModule(hostName, configuration))
                .build();
    }

    public EnterpriseAddressUIComponent buildEnterpriseAddressUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseAddressUIComponent.builder()
                .enterpriseAddressUIModule(new EnterpriseAddressUIModule(hostName, configuration))
                .build();
    }
}
