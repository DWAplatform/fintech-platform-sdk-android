package com.fintechplatform.android.enterprise.documents.ui;

import com.fintechplatform.android.enterprise.api.EnterpriseAPI;
import com.fintechplatform.android.enterprise.db.documents.EnterpriseDocumentsPersistanceDB;
import com.fintechplatform.android.images.ImageHelper;
import com.fintechplatform.android.models.DataAccount;

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
