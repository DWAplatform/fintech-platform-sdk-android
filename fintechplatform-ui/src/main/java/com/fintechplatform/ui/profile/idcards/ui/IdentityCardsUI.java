package com.fintechplatform.ui.profile.idcards.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.models.DataAccount;

public class IdentityCardsUI {

    private String hostName;
    private DataAccount configuration;

    public static IdentityCardsUI instance;

    public IdentityCardsUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    protected IdentityCardsViewComponent buildIdCardsViewComponent(Context context, IdentityCardsContract.View view) {
        return DaggerIdentityCardsViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .identityCardsPresenterModule(new IdentityCardsPresenterModule(view, instance.configuration))
                .profileAPIModule(new ProfileAPIModule(instance.hostName))
                .build();
    }

    public IdentityCardsViewComponent createIdCardsViewComponent(Context context, IdentityCardsContract.View view) {
        return instance.buildIdCardsViewComponent(context, view);
    }
    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, IdentityCardsActivity.class);
        context.startActivity(intent);
    }
}
