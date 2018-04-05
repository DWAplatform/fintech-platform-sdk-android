package com.fintechplatform.ui.profile.db.documents;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DocumentsPersistanceDBModule {

    @Provides
    @Singleton
    DocumentsPersistanceDB providesDocumentsPersistanceDB(DocumentsDB documentsDB) {
        return new DocumentsPersistanceDB(documentsDB);
    }

    @Provides
    @Singleton
    DocumentsDB providesDocumentsDB() {
        return new DocumentsDB();
    }
}
