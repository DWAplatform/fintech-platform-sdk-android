package com.fintechplatform.ui.sct;

import com.fintechplatform.ui.sct.ui.SctUI;
import com.fintechplatform.ui.sct.ui.SctUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 22/02/18.
 */
@Singleton
@Component (modules = {
        SctUIModule.class
})
public interface SctUIComponent {
    SctUI getSctUI();
}
