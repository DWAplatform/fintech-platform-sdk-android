package com.fintechplatform.ui.secure3d.ui;

import android.content.Context;
import android.content.Intent;

public class Secure3DUI {

    public void start(Context context, String redirecturl) {
        Intent intent = new Intent(context, Secure3DActivity.class);
        intent.putExtra("redirecturl", redirecturl);
        context.startActivity(intent);
    }

    public Secure3DFragment createFragment(String redirecturl) {
        return Secure3DFragment.Companion.newInstance(redirecturl);
    }

    public static class Builder {
        public static Secure3DViewComponent build3DsecureComponent(Secure3DContract.View v) {
            return DaggerSecure3DViewComponent.builder()
                    .secure3DPresenterModule(new Secure3DPresenterModule(v))
                    .build();
        }
    }

}
