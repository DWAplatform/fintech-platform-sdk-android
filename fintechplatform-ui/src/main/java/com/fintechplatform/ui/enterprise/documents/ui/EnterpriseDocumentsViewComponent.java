package com.fintechplatform.ui.enterprise.documents.ui;

import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.ui.enterprise.db.documents.EnterpriseDocumentsPersistanceDBModule;
import com.fintechplatform.ui.images.ImageHelperModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        EnterpriseDocumentsPresenterModule.class,
        EnterpriseAPIModule.class,
        EnterpriseDocumentsUIModule.class,
        NetModule.class,
        ImageHelperModule.class,
        AlertHelpersModule.class,
        EnterpriseDocumentsPersistanceDBModule.class,
        LogModule.class
})
public interface EnterpriseDocumentsViewComponent {
    void inject(EnterpriseDocumentsActivity activity);
}
