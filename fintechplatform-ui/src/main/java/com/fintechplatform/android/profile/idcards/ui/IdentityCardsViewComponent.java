package com.fintechplatform.android.profile.idcards.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;
import com.fintechplatform.android.profile.db.documents.DocumentsPersistanceDBModule;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDBModule;

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
