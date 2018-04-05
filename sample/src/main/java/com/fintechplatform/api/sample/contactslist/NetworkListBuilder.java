package com.fintechplatform.api.sample.contactslist;

import com.fintechplatform.api.models.DataAccount;
import com.fintechplatform.api.sample.contactslist.ui.NetworkUsersUIModule;

/**
 * Created by ingrid on 09/02/18.
 */

public class NetworkListBuilder {

    public NetworkListUIComponent buildNetworkComponent(String hostname, DataAccount config) {
        return DaggerNetworkListUIComponent.builder()
                .networkUsersUIModule(new NetworkUsersUIModule(config, hostname))
                .build();
    }
}
