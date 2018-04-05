package com.fintechplatform.ui.enterprise.db.documents;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseDocumentsPersistanceDBModule {

    @Provides
    @Singleton
    EnterpriseDocumentsPersistanceDB providesEnterpriseDocumentsDB(EnterpriseDocumentsDB documentsDB) {
        return new EnterpriseDocumentsPersistanceDB(documentsDB);
    }

    @Provides
    @Singleton
    EnterpriseDocumentsDB providesDocumentsDB() {
        return new EnterpriseDocumentsDB();
    }
}
