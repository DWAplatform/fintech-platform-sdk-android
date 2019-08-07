package com.fintechplatform.ui.iban.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.iban.api.IbanAPIModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.R;
import com.fintechplatform.ui.models.DataAccount;

public class IbanUI {

    public IBANContract.Presenter getIbanPresenter(Context context, IBANContract.View view, String hostname, DataAccount dataAccount) {
        return DaggerIBANPrensenterComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .iBANPresenterModule(new IBANPresenterModule(view, dataAccount))
                .ibanAPIModule(new IbanAPIModule(hostname))
                .profileAPIModule(new ProfileAPIModule(hostname))
                .enterpriseAPIModule(new EnterpriseAPIModule(hostname))
                .build().getPresenter();
    }
/*

    public static IBANViewComponent createIBANViewComponent(Context context, IBANContract.View view) {
        return instance.buildIbanViewComponent(context, view);
    }

    protected IBANViewComponent buildIbanViewComponent(Context context, IBANContract.View view) {
        return DaggerIBANViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostname))
                .iBANPresenterModule(new IBANPresenterModule(view, instance.configuration))
                .ibanAPIModule(new IbanAPIModule(instance.hostname))
                .profileAPIModule(new ProfileAPIModule(instance.hostname))
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostname))
                .build();
    }
*/
    public void startActivity(Context context, String hostname, DataAccount dataAccount){
        Intent intent = new Intent(context, IBANActivity.class);
        Bundle args = new Bundle();
        args.putString("hostname", hostname);
        args.putParcelable("dataAccount", dataAccount);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    public void startFragment(FragmentActivity activity, String hostname, DataAccount dataAccount) {
        IBANFragment frag = IBANFragment.Companion.newInstance(hostname, dataAccount);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentContainer, frag)
                .commit();
    }
}
