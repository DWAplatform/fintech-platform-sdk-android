package com.fintechplatform.ui.profile.jobinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import javax.inject.Inject


/**
 * Shows info about job and salary income
 */
class JobInfoActivity: AppCompatActivity(), JobInfoContract.Navigation {

    @Inject lateinit var presenter: JobInfoContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_with_fragment)

        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                val frag = JobInfoFragment.newInstance(hostname, dataAccount)
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.contentContainer, frag, JobInfoFragment::class.java.canonicalName)
                        .commit()
            }
        }

    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun backFromJobInfo() {
        onBackPressed()
    }
}