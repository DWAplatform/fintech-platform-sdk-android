package com.fintechplatform.ui.enterprise;

import com.fintechplatform.ui.enterprise.documents.DaggerEnterpriseDocumentsUIComponent;
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsUIComponent;
import com.fintechplatform.ui.enterprise.documents.ui.EnterpriseDocumentsUIModule;
import com.fintechplatform.ui.models.DataAccount;

public class EnterpriseBuilder {



    public EnterpriseDocumentsUIComponent buildEnterpriseDocumentsUIComponent(String hostName, DataAccount configuration) {
        return DaggerEnterpriseDocumentsUIComponent.builder()
                .enterpriseDocumentsUIModule(new EnterpriseDocumentsUIModule(configuration, hostName))
                .build();
    }
}
