package com.fintechplatform.ui.profile.idcards.ui;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.images.ImageHelperModule;
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDBModule;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        IdentityCardsPresenterModule.class,
        ProfileAPIModule.class,
        NetModule.class,
        LogModule.class,
        UsersPersistanceDBModule.class,
        DocumentsPersistanceDBModule.class,
        AlertHelpersModule.class,
        ImageHelperModule.class
})
public interface IdentityCardsViewComponent {
    void inject(IdentityCardsActivity activity);
}
