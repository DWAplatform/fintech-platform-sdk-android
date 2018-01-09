package com.dwaplatform.android.profile.jobinfo;

import com.dwaplatform.android.profile.jobinfo.ui.JobInfoContract;
import com.dwaplatform.android.profile.jobinfo.ui.JobInfoUI;
import com.dwaplatform.android.profile.jobinfo.ui.JobInfoUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        JobInfoUIModule.class
})
public interface JobInfoUIComponet {
    JobInfoUI getJobInfoUI();
}
