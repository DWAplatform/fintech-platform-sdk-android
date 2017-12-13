package com.dwaplatform.android.secure3d.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ingrid on 12/12/17.
 */

public class Secure3DUI {

    protected static Secure3DUI instance;

    public Secure3DUI() {

    }

    protected Secure3DViewComponent build3DsecureComponent(Secure3DContract.View v) {
        return DaggerSecure3DComponent.builder()
                .secure3DPresenterModule(new Secure3DPresenterModule(v))
                .build();
    }

    public static Secure3DViewComponent create3DComponent(Secure3DContract.View v) {
        return instance.build3DsecureComponent(v);
    }

    public void start(Context context, String redirecturl) {
        instance = this;
        Intent intent = new Intent(context, Secure3DActivity.class);
        intent.putExtra("redirecturl", redirecturl);
        context.startActivity(intent);
    }

}
