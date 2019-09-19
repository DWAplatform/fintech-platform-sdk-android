package com.fintechplatform.ui.profile.jobinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.jobinfo.di.DaggerJobInfoViewComponent
import com.fintechplatform.ui.profile.jobinfo.di.JobInfoPresenterModule
import com.fintechplatform.ui.profile.jobinfo.di.JobInfoViewComponent


class JobInfoUI(private val hostName: String, private val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, JobInfoActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(): JobInfoFragment {
        return JobInfoFragment.newInstance(hostName, configuration)
    }


    object Builder {
        fun buildJobInfoComponent(context: Context, view: JobInfoContract.View, hostName: String, dataAccount: DataAccount): JobInfoViewComponent {
            return DaggerJobInfoViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .jobInfoPresenterModule(JobInfoPresenterModule(view, dataAccount))
                    .profileAPIModule(ProfileAPIModule(hostName))
                    .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                    .build()
        }
    }
}
