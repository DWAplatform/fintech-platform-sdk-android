package com.fintechplatform.ui.secure3d.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.R

class Secure3DActivity: AppCompatActivity(), Secure3DContract.Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)

        val secureCodeUrl = intent.getStringExtra("redirecturl")


    }

    override fun goBackwardFrom3dSecure() {
        finish()
    }
}