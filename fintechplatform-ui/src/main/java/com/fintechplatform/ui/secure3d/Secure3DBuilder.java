package com.fintechplatform.ui.secure3d;

public class Secure3DBuilder {
    public Secure3DUIComponent buildSecure3DUIComponent() {
        return DaggerSecure3DUIComponent.builder()
                .build();
    }
}
