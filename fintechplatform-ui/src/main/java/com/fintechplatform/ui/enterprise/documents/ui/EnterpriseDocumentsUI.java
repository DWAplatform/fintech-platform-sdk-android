package com.fintechplatform.ui.enterprise.documents.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.models.DataAccount;

public class EnterpriseDocumentsUI {

    private DataAccount configuration;
    private String hostName;

    protected static EnterpriseDocumentsUI instance;

    public EnterpriseDocumentsUI(DataAccount configuration, String hostName) {
        this.configuration = configuration;
        this.hostName = hostName;
    }

    protected EnterpriseDocumentsViewComponent buildEnterpriseDocumentsViewComponent(Context context, EnterpriseDocumentsContract.View view) {
        return DaggerEnterpriseDocumentsViewComponent.builder()
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostName))
                .enterpriseDocumentsPresenterModule(new EnterpriseDocumentsPresenterModule(instance.configuration, view))
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .enterpriseDocumentsUIModule(new EnterpriseDocumentsUIModule(instance.configuration, instance.hostName))
                .build();
    }

    public EnterpriseDocumentsViewComponent createEnterpriseDocumentsViewComponent(Context context, EnterpriseDocumentsContract.View view) {
        return instance.buildEnterpriseDocumentsViewComponent(context,view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, EnterpriseDocumentsActivity.class);
        context.startActivity(intent);
    }
}
