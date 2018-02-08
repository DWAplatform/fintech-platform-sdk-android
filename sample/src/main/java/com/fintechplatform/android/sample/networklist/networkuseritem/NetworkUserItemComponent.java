package com.dwafintech.dwapay.main.networklist.networkuseritem;

import com.dwafintech.dwapay.main.networklist.models.NetworkUsersPersistanceModule;
import com.fintechplatform.android.images.ImageHelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        NetworkUsersItemPresenterModule.class,
        NetworkUsersPersistanceModule.class,
        ImageHelperModule.class
})
public interface NetworkUserItemComponent {
    void inject(NetworkUserViewHolder viewHolder);
}
