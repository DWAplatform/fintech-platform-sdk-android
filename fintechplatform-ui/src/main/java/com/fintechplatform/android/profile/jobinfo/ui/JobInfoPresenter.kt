package com.fintechplatform.android.profile.jobinfo.ui

import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.net.NetHelper
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.fintechplatform.android.profile.models.UserJobInfo
import javax.inject.Inject

class JobInfoPresenter @Inject constructor(val view: JobInfoContract.View,
                                           val api: ProfileAPI,
                                           val configuration: DataAccount,
                                           val usersPersistanceDB: UsersPersistanceDB): JobInfoContract.Presenter, IncomePickerDialog.OnPickSalaryIncome {

    lateinit var salaries: Array<String>
    var token:String?=null

    override fun initializate(array: Array<String>) {

        salaries = array
        reloadUserData()

    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(configuration.accessToken, configuration.ownerId, configuration.tenantId) { profile, exception ->

            if (exception != null) {
                return@searchUser
            }

            if (profile == null) {
                return@searchUser
            }

            view.enableAllTexts(true)

            val userjobinfo = UserJobInfo(
                    configuration.ownerId,
                    configuration.tenantId,
                    profile.jobinfo,
                    profile.income
            )

            usersPersistanceDB.saveJobInfo(userjobinfo)

            reloadUserData()

            view.setBackwardText()
            view.enableConfirmButton(false)
        }
    }

    override fun onAbort() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun onConfirm() {
        view.hideKeyboard()
        view.showWaiting()

        val jobInfo = UserJobInfo(configuration.ownerId, configuration.tenantId, view.getJobInfoText(), getKeySalaryValue( view.getIncomeText() ) )
        api.jobInfo(
                configuration.accessToken,
                jobInfo
                ) { optuserprofilereply, opterror ->

            view.hideWaiting()

            if (opterror != null) {
                handleErrors(opterror)
                return@jobInfo
            }

            if (optuserprofilereply?.userid.isNullOrBlank()) {
                return@jobInfo
            }

            val profile = UserJobInfo(configuration.ownerId,
                    configuration.tenantId,
                    view.getJobInfoText(),
                    getKeySalaryValue(view.getIncomeText())
            )

            view.enableConfirmButton(false)
            usersPersistanceDB.saveJobInfo(profile)

            view.goBack()
        }

    }

    override fun refreshConfirmButton() {
        if (view.getJobInfoText().isNotBlank() &&
                view.getIncomeText().isNotBlank()) {
            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }

    override fun onIncomeClick() {
        view.showIncomeDialog()
    }

    override fun salaryPicked(income: String) {
        view.setIcomeText(income)
    }


    private fun reloadUserData() {
        val userProfile = usersPersistanceDB.userProfile(configuration.ownerId)
        userProfile?.let {
            view.setJobInfoText(it.jobinfo ?: "")
            view.setIcomeText(setSalaryValue(it.income ?: ""))
        }
    }

    private fun getKeySalaryValue(income: String): String {
        return salaries.indexOf(income).plus(1).toString()
    }

    private fun setSalaryValue(key: String): String {
        if (key.isNotBlank()) {
            val index = key.toInt().minus(1)
            return salaries[index]
        } else return ""
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showInternalError()
        }
    }
}