package com.fintechplatform.android.sample.contactslist;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.sample.contactslist.ui.NetworkUsersUIModule;

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
