package com.fintechplatform.ui.profile.db.documents

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DocumentsPersistanceDBModule {

    @Provides
    @Singleton
    internal fun providesDocumentsPersistanceDB(documentsDB: DocumentsDB): DocumentsPersistanceDB = DocumentsPersistanceDB(documentsDB)

    @Provides
    @Singleton
    internal fun providesDocumentsDB(): DocumentsDB = DocumentsDB()
}