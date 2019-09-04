package com.fintechplatform.ui.profile.idcards.ui;

import com.fintechplatform.api.profile.api.ProfileAPI;
import com.fintechplatform.ui.images.ImageHelper;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDB;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdentityCardsPresenterModule {

    private IdentityCardsContract.View view;
    private DataAccount configuration;

    public IdentityCardsPresenterModule(IdentityCardsContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    IdentityCardsContract.Presenter providesIdentityCardsPresenter(ProfileAPI api,
                                                                   DocumentsPersistanceDB documents,
                                                                   UsersPersistanceDB users,
                                                                   ImageHelper imageHelper) {
        return new IdentityCardsPresenter(view, api, configuration, documents, users, imageHelper);
    }
}
