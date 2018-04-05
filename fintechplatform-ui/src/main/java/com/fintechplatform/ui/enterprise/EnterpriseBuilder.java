package com.fintechplatform.ui.enterprise;


import com.fintechplatform.ui.enterprise.address.DaggerEnterpriseAddressUIComponent;
import com.fintechplatform.ui.enterprise.address.EnterpriseAddressUIComponent;
import com.fintechplatform.ui.enterprise.address.ui.EnterpriseAddressUIModule;
import com.fintechplatform.ui.enterprise.contacts.DaggerEnterpriseContactsUIComponent;
import com.fintechplatform.ui.enterprise.contacts.EnterpriseContactsUIComponent;
import com.fintechplatform.ui.enterprise.contacts.ui.EnterpriseContactsUIModule;
import com.fintechplatform.ui.enterprise.documents.DaggerEnterpriseDocumentsUIComponent;
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsUIComponent;
import com.fintechplatform.ui.enterprise.documents.ui.EnterpriseDocumentsUIModule;
import com.fintechplatform.ui.enterprise.info.DaggerEnterpriseInfoUIComponent;
import com.fintechplatform.ui.enterprise.info.EnterpriseInfoUIComponent;
import com.fintechplatform.ui.enterprise.info.ui.EnterpriseInfoUIModule;
import com.fintechplatform.ui.models.DataAccount;

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
