package com.fintechplatform.ui.sct;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.sct.ui.SctUIModule;

/**
 * Created by ingrid on 22/02/18.
 */

public class SctBuilder {
    public SctUIComponent createSctUIComponent(String hostName, DataAccount configuration) {
        return DaggerSctUIComponent.builder()
                .sctUIModule(new SctUIModule(configuration, hostName))
                .build();
    }

}
