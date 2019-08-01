package com.fintechplatform.ui.iban.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * UI for IBAN account number and mandatory personal data linked to.
 */
class IBANActivity: FragmentActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val frag = IBANFragment.newInstance()
        setContentView(R.layout.activity_with_fragment)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, frag)
                .commit()

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}