package com.dwaplatform.android.enterprise.contacts;

import com.dwaplatform.android.enterprise.contacts.ui.EnterpriseContactsUI;
import com.dwaplatform.android.enterprise.contacts.ui.EnterpriseContactsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseContactsUIModule.class )
public interface EnterpriseContactsUIComponent {
    EnterpriseContactsUI getEnterpriseContactsUI();
}
