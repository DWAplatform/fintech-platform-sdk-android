package com.dwaplatform.android.profile.jobinfo.ui

import com.dwaplatform.android.auth.keys.KeyChain
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.profile.api.ProfileAPI
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB
import com.dwaplatform.android.profile.models.UserJobInfo
import javax.inject.Inject

class JobInfoPresenter @Inject constructor(val view: JobInfoContract.View,
                                           val api: ProfileAPI,
                                           val configuration: DataAccount,
                                           val keyChain: KeyChain,
                                           val usersPersistanceDB: UsersPersistanceDB): JobInfoContract.Presenter, IncomePickerDialog.OnPickSalaryIncome {

    lateinit var salaries: Array<String>

    override fun initializate(array: Array<String>) {

        salaries = array
        reloadUserData()

    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(keyChain["tokenuser"], configuration.userId ) { profile, exception ->

            if (exception != null) {
                return@searchUser
            }

            if (profile == null) {
                return@searchUser
            }

            view.enableAllTexts(true)

            val userjobinfo = UserJobInfo(
                    configuration.userId,
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

        val jobInfo = UserJobInfo(configuration.userId, view.getJobInfoText(), getKeySalaryValue( view.getIncomeText() ) )
        api.jobInfo(
                keyChain["tokenuser"],
                jobInfo
                ) { optuserprofilereply, opterror ->

            view.hideWaiting()

            if (opterror != null) {
                view.showCommunicationInternalNetwork()
                return@jobInfo
            }

            if (optuserprofilereply?.userid.isNullOrBlank()) {
                view.showCommunicationInternalNetwork()
                return@jobInfo
            }


            val profile = UserJobInfo(
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
        val userProfile = usersPersistanceDB.userProfile(configuration.userId)
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
}