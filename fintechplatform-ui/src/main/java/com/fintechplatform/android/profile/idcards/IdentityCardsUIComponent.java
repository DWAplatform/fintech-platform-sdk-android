package com.fintechplatform.android.profile.idcards;

import com.fintechplatform.android.profile.idcards.ui.IdentityCardsUI;
import com.fintechplatform.android.profile.idcards.ui.IdentityCardsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = IdentityCardsUIModule.class)
public interface IdentityCardsUIComponent {
    IdentityCardsUI getIdentityCardsUI();
}
