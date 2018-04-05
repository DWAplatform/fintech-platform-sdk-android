package com.fintechplatform.ui.enterprise.documents;

import com.fintechplatform.ui.enterprise.documents.ui.EnterpriseDocumentsUI;
import com.fintechplatform.ui.enterprise.documents.ui.EnterpriseDocumentsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseDocumentsUIModule.class)
public interface EnterpriseDocumentsUIComponent {
    EnterpriseDocumentsUI getEnterpriseDocumentsUI();
}
