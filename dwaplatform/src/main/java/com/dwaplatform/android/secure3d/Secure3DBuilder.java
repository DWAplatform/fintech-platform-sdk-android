package com.dwaplatform.android.secure3d;

import com.dwaplatform.android.secure3d.ui.DaggerSecure3DUIComponent;

/**
 * Created by ingrid on 12/12/17.
 */

public class Secure3DBuilder {
    public Secure3DUIComponent buildSecure3DUIComponent() {
        return DaggerSecure3DUIComponent.builder()
                .build();
    }
}
