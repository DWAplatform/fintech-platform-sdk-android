package com.dwaplatform.android.enterprise.documents;

import com.dwaplatform.android.enterprise.documents.ui.EnterpriseDocumentsUI;
import com.dwaplatform.android.enterprise.documents.ui.EnterpriseDocumentsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseDocumentsUIModule.class)
public interface EnterpriseDocumentsUIComponent {
    EnterpriseDocumentsUI getEnterpriseDocumentsUI();
}
