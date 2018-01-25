package com.dwaplatform.android.secure3d.ui;

import dagger.Module;

/**
 * Created by ingrid on 13/12/17.
 */
@Module
public class Secure3DMockUI extends Secure3DUI {

    public Secure3DMockUI() { instance = this; }

    @Override
    protected Secure3DViewComponent build3DsecureComponent(Secure3DContract.View v) {
        return DaggerMockSecure3DComponent.builder().build();
    }
}
