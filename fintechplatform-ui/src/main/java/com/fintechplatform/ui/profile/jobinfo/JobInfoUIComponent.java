package com.fintechplatform.ui.profile.jobinfo;

import com.fintechplatform.ui.profile.jobinfo.ui.JobInfoUI;
import com.fintechplatform.ui.profile.jobinfo.ui.JobInfoUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        JobInfoUIModule.class
})
public interface JobInfoUIComponent {
    JobInfoUI getJobInfoUI();
}
