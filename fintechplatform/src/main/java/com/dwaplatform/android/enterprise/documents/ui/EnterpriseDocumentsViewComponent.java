package com.dwaplatform.android.enterprise.documents.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.enterprise.db.documents.EnterpriseDocumentsPersistanceDBModule;
import com.dwaplatform.android.images.ImageHelperModule;
import com.dwaplatform.android.log.LogModule;

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
