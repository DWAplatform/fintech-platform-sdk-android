package com.fintechplatform.api.sample.contactslist.ui.networkuseritem;

import com.fintechplatform.api.images.ImageHelperModule;
import com.fintechplatform.api.sample.contactslist.models.NetworkUsersPersistanceModule;

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
