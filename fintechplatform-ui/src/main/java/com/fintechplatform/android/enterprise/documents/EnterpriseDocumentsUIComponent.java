package com.fintechplatform.android.enterprise.documents;

import com.fintechplatform.android.enterprise.documents.ui.EnterpriseDocumentsUI;
import com.fintechplatform.android.enterprise.documents.ui.EnterpriseDocumentsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseDocumentsUIModule.class)
public interface EnterpriseDocumentsUIComponent {
    EnterpriseDocumentsUI getEnterpriseDocumentsUI();
}
