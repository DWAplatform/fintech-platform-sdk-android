package com.fintechplatform.ui.iban.ui

import android.support.v4.app.Fragment
import com.fintechplatform.ui.di.Injectable


class IBANFragment: Fragment(), Injectable {

    companion object {
        fun newInstance() : IBANFragment {
            return IBANFragment()
        }
    }
}