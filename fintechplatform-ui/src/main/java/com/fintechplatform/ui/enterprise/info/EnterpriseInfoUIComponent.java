package com.fintechplatform.ui.enterprise.info;

import com.fintechplatform.ui.enterprise.info.ui.EnterpriseInfoUI;
import com.fintechplatform.ui.enterprise.info.ui.EnterpriseInfoUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = EnterpriseInfoUIModule.class)
public interface EnterpriseInfoUIComponent{
    EnterpriseInfoUI getEnterpriseInfoUI();
}
