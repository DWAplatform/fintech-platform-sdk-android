package com.fintechplatform.android.enterprise;


import com.fintechplatform.android.enterprise.address.DaggerEnterpriseAddressUIComponent;
import com.fintechplatform.android.enterprise.address.EnterpriseAddressUIComponent;
import com.fintechplatform.android.enterprise.address.ui.EnterpriseAddressUIModule;
import com.fintechplatform.android.enterprise.contacts.DaggerEnterpriseContactsUIComponent;
import com.fintechplatform.android.enterprise.contacts.EnterpriseContactsUIComponent;
import com.fintechplatform.android.enterprise.contacts.ui.EnterpriseContactsUIModule;
import com.fintechplatform.android.enterprise.documents.DaggerEnterpriseDocumentsUIComponent;
import com.fintechplatform.android.enterprise.documents.EnterpriseDocumentsUIComponent;
import com.fintechplatform.android.enterprise.documents.ui.EnterpriseDocumentsUIModule;
import com.fintechplatform.android.enterprise.info.DaggerEnterpriseInfoUIComponent;
import com.fintechplatform.android.enterprise.info.EnterpriseInfoUIComponent;
import com.fintechplatform.android.enterprise.info.ui.EnterpriseInfoUIModule;
import com.fintechplatform.android.models.DataAccount;

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

    public EnterpriseDocumentsUIComponent buildEnterpriseDocumentsUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseDocumentsUIComponent.builder()
                .enterpriseDocumentsUIModule(new EnterpriseDocumentsUIModule(configuration, hostName))
                .build();
    }
}
