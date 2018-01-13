package com.dwaplatform.android.profile.idcards.ui;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.images.ImageHelperModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.api.ProfileAPIModule;
import com.dwaplatform.android.profile.db.documents.DocumentsPersistanceDBModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

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
        ImageHelperModule.class
})
public interface IdentityCardsViewComponent {
    void inject(IdentityCardsActivity activity);
}
