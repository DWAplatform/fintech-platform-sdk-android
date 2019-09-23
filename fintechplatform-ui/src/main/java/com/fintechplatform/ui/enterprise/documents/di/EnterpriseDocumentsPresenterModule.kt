package com.fintechplatform.ui.enterprise.documents.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.ui.enterprise.db.documents.EnterpriseDocumentsPersistanceDB
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsContract
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsPresenter
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class EnterpriseDocumentsPresenterModule(private val configuration: DataAccount, private val view: EnterpriseDocumentsContract.View) {

    @Provides
    @Singleton
    internal fun providesEnterpriseDocumentsPresenter(api: EnterpriseAPI, dbDocuments: EnterpriseDocumentsPersistanceDB, imageHelper: ImageHelper): EnterpriseDocumentsContract.Presenter {
        return EnterpriseDocumentsPresenter(view, api, configuration, dbDocuments, imageHelper)
    }
}