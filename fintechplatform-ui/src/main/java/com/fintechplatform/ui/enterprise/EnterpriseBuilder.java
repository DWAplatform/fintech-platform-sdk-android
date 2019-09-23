package com.fintechplatform.ui.enterprise;

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

    public EnterpriseDocumentsUIComponent buildEnterpriseDocumentsUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseDocumentsUIComponent.builder()
                .enterpriseDocumentsUIModule(new EnterpriseDocumentsUIModule(configuration, hostName))
                .build();
    }
}
