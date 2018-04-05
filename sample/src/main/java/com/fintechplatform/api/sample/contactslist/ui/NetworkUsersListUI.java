package com.fintechplatform.api.sample.contactslist.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.models.DataAccount;
import com.fintechplatform.api.net.net.NetModule;
import com.fintechplatform.api.sample.contactslist.ui.networkuseritem.DaggerNetworkUserItemComponent;
import com.fintechplatform.api.sample.contactslist.ui.networkuseritem.NetworkUserItemComponent;
import com.fintechplatform.api.sample.contactslist.ui.networkuseritem.NetworkUserItemContract;
import com.fintechplatform.api.sample.contactslist.ui.networkuseritem.NetworkUsersItemPresenterModule;

public class NetworkUsersListUI {

    private String hostName;
    private DataAccount dataAccount;

    public static NetworkUsersListUI instace;

    public NetworkUsersListUI(String hostName, DataAccount dataAccount) {
        this.hostName = hostName;
        this.dataAccount = dataAccount;
    }

    protected NetworkUsersListComponent createNetworkListComponent(Context context, NetworkUsersListContract.View view) {
        return DaggerNetworkUsersListComponent.builder()
                .networkUsersListPresenterModule(new NetworkUsersListPresenterModule(view, instace.dataAccount))
                .netModule(new NetModule(Volley.newRequestQueue(context), instace.hostName))
                .build();
    }

    public NetworkUsersListComponent buildNetworkListComponent(Context context, NetworkUsersListContract.View view) {
        return instace.createNetworkListComponent(context, view);
    }

    protected NetworkUserItemComponent createUserViewHolderComponet(NetworkUserItemContract.View view){
        return DaggerNetworkUserItemComponent.builder()
                .networkUsersItemPresenterModule(new NetworkUsersItemPresenterModule(view))
                .build();
    }

    public NetworkUserItemComponent buildUserViewHolderComponent(NetworkUserItemContract.View view) {
        return instace.createUserViewHolderComponet(view);
    }

    public void start(Context context){
        instace = this;
        Intent intent = new Intent(context, NetworkUsersListActivity.class);
        context.startActivity(intent);
    }
}
