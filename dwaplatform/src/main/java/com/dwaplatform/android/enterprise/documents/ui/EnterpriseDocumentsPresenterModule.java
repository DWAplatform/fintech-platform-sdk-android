package com.dwaplatform.android.enterprise.documents.ui;

import com.dwaplatform.android.enterprise.api.EnterpriseAPI;
import com.dwaplatform.android.enterprise.db.documents.EnterpriseDocumentsPersistanceDB;
import com.dwaplatform.android.images.ImageHelper;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseDocumentsPresenterModule {
    private DataAccount configuration;
    private EnterpriseDocumentsContract.View view;

    public EnterpriseDocumentsPresenterModule(DataAccount configuration, EnterpriseDocumentsContract.View view) {
        this.configuration = configuration;
        this.view = view;
    }

    @Provides
    @Singleton
    EnterpriseDocumentsContract.Presenter providesEnterpriseDocumentsPresenter(EnterpriseAPI api, EnterpriseDocumentsPersistanceDB dbDocuments, ImageHelper imageHelper) {
        return new EnterpriseDocumentsPresenter(view, api, configuration, dbDocuments, imageHelper);
    }
}
