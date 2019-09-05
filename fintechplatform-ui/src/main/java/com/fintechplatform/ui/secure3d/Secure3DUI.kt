package com.fintechplatform.ui.secure3d

import android.content.Context
import android.content.Intent
import com.fintechplatform.ui.secure3d.di.DaggerSecure3DViewComponent
import com.fintechplatform.ui.secure3d.di.Secure3DPresenterModule
import com.fintechplatform.ui.secure3d.di.Secure3DViewComponent


class Secure3DUI {

    fun start(context: Context, redirecturl: String) {
        val intent = Intent(context, Secure3DActivity::class.java)
        intent.putExtra("redirecturl", redirecturl)
        context.startActivity(intent)
    }

    fun createFragment(redirecturl: String): Secure3DFragment {
        return Secure3DFragment.newInstance(redirecturl)
    }

    object Builder {
        /**
         * Resolve with default dependencies for cash in view component
         */
        fun build3DsecureComponent(v: Secure3DContract.View): Secure3DViewComponent {
            return DaggerSecure3DViewComponent.builder()
                    .secure3DPresenterModule(Secure3DPresenterModule(v))
                    .build()
        }
    }

}