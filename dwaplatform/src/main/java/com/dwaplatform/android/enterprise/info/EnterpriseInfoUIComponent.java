package com.dwaplatform.android.enterprise.info;

import com.dwaplatform.android.enterprise.info.ui.EnterpriseInfoUI;
import com.dwaplatform.android.enterprise.info.ui.EnterpriseInfoUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseInfoUIModule.class)
public interface EnterpriseInfoUIComponent{
    EnterpriseInfoUI getEnterpriseInfoUI();
}
