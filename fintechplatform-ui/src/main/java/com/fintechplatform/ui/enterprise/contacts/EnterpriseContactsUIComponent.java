package com.fintechplatform.ui.enterprise.contacts;

import com.fintechplatform.ui.enterprise.contacts.ui.EnterpriseContactsUI;
import com.fintechplatform.ui.enterprise.contacts.ui.EnterpriseContactsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseContactsUIModule.class )
public interface EnterpriseContactsUIComponent {
    EnterpriseContactsUI getEnterpriseContactsUI();
}
