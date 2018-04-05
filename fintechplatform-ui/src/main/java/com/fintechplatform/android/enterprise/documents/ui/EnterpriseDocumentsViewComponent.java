package com.fintechplatform.android.enterprise.documents.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.enterprise.db.documents.EnterpriseDocumentsPersistanceDBModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

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
