package com.fintechplatform.ui.secure3d.ui

interface Secure3DUIFactory {
    fun create3DComponent(view: Secure3DContract.View): Secure3DViewComponent
}