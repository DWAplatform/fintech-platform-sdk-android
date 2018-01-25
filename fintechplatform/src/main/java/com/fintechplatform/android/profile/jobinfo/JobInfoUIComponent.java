package com.fintechplatform.android.profile.jobinfo;

import com.fintechplatform.android.profile.jobinfo.ui.JobInfoUI;
import com.fintechplatform.android.profile.jobinfo.ui.JobInfoUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        JobInfoUIModule.class
})
public interface JobInfoUIComponent {
    JobInfoUI getJobInfoUI();
}
