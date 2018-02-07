package com.fintechplatform.android.transfer.contactslist.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPIModule;
import com.fintechplatform.android.transfer.contactslist.ui.networkuseritem.DaggerNetworkUserItemComponent;
import com.fintechplatform.android.transfer.contactslist.ui.networkuseritem.NetworkUserItemComponent;
import com.fintechplatform.android.transfer.contactslist.ui.networkuseritem.NetworkUserItemContract;
import com.fintechplatform.android.transfer.contactslist.ui.networkuseritem.NetworkUsersItemPresenterModule;
import com.fintechplatform.android.transfer.ui.TransferUI;

public class NetworkUsersListUI {

    private String hostName;
    private DataAccount dataAccount;
    private TransferUI transferUI;

    public static NetworkUsersListUI instace;

    public NetworkUsersListUI(String hostName, DataAccount dataAccount, TransferUI transferUI) {
        this.hostName = hostName;
        this.dataAccount = dataAccount;
        this.transferUI = transferUI;
    }

    protected NetworkUsersListComponent createNetworkListComponent(Context context, NetworkUsersListContract.View view) {
        return DaggerNetworkUsersListComponent.builder()
                .networkUsersListPresenterModule(new NetworkUsersListPresenterModule(view, instace.dataAccount))
                .netModule(new NetModule(Volley.newRequestQueue(context), instace.hostName))
                .transferAPIModule(new TransferAPIModule(instace.hostName))
                .transfersUIHelperModule(new TransfersUIHelperModule(instace.transferUI))
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
