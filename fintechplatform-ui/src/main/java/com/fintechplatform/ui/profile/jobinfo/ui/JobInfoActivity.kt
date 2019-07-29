package com.fintechplatform.ui.profile.jobinfo.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_profile_job.*
import javax.inject.Inject


/**
 * Shows info about job and salary income
 */
class JobInfoActivity: AppCompatActivity(), JobInfoContract.View {

    @Inject lateinit var presenter: JobInfoContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        JobInfoUI.instance.createJobInfoComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_profile_job)

        jobText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        incomeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        backwardButton.setOnClickListener { presenter.onAbort() }

        confirmButton.setOnClickListener { presenter.onConfirm() }

        incomeText.setOnClickListener { presenter.onIncomeClick() }

        presenter.initializate(resources.getStringArray(R.array.salaries))
    }

    override fun onBackPressed() {
        presenter.onAbort()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setBackwardText() {
        backwardButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun getJobInfoText(): String {
        return jobText.text.toString()
    }

    override fun getIncomeText(): String {
        return incomeText.text.toString()
    }

    override fun setJobInfoText(jobInfo: String) {
        jobText.setText(jobInfo)
    }

    override fun setIcomeText(income: String) {
        incomeText.setText(income)
    }

    override fun showIncomeDialog() {
        val dialog = IncomePickerDialog.newInstance()
        dialog.show(supportFragmentManager, "income_dialog")
    }

    override fun enableAllTexts(isEnables: Boolean) {
        jobText.isEnabled = isEnables
        incomeText.isEnabled = isEnables
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun showInternalError() {
        alertHelper.internalError(this)
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }
}