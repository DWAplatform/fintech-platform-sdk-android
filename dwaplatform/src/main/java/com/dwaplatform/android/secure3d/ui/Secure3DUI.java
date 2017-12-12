package com.dwaplatform.android.secure3d.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ingrid on 12/12/17.
 */

public class Secure3DUI {

    protected static Secure3DUI instance;

    public Secure3DUI() {
        instance = this;
    }

    protected Secure3DComponent build3DsecureComponent() {
        return DaggerSecure3DComponent.builder().build();
    }

    public static Secure3DComponent create3DUIComponent() {
        return instance.build3DsecureComponent();
    }

    public void start(Context context, String redirecturl) {
        Intent intent = new Intent(context, Secure3DActivity.class);
        intent.putExtra("redirecturl", redirecturl);
        context.startActivity(intent);
    }

}
