package com.dwafintech.dwapay.main.networklist;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwafintech.dwapay.main.networklist.networkuseritem.DaggerNetworkUserItemComponent;
import com.dwafintech.dwapay.main.networklist.networkuseritem.NetworkUserItemComponent;
import com.dwafintech.dwapay.main.networklist.networkuseritem.NetworkUsersItemPresenterModule;
import com.dwafintech.dwapay.main.networklist.ui.networkuseritem.NetworkUserItemContract;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPIModule;
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
        Intent intent = new Intent(context, NetworkUsersListFragment.class);
        context.startActivity(intent);
    }
}
