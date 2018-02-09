package com.fintechplatform.android.sample.contactslist.ui.networkuseritem;

import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.sample.contactslist.models.NetworkUsersPersistanceModule;

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
