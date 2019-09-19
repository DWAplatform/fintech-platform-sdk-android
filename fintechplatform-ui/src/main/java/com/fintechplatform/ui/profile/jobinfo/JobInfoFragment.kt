package com.fintechplatform.ui.profile.jobinfo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.jobinfo.di.JobInfoViewComponent
import kotlinx.android.synthetic.main.activity_profile_job.*
import kotlinx.android.synthetic.main.activity_profile_job.view.*
import javax.inject.Inject


open class JobInfoFragment: Fragment(), JobInfoContract.View {

    @Inject
    lateinit var presenter: JobInfoContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var navigation: JobInfoContract.Navigation? = null

    open fun createJobInfoComponent(context: Context, view: JobInfoContract.View, hostName: String, dataAccount: DataAccount): JobInfoViewComponent {
         return JobInfoUI.Builder.buildJobInfoComponent(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profile_job, container, false)

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createJobInfoComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.jobText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.incomeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        view.incomeText.setOnClickListener { presenter.onIncomeClick() }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initializate(resources.getStringArray(R.array.salaries))
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? JobInfoContract.Navigation)?.let {
            navigation = it
        }?: Log.e(JobInfoFragment::class.java.canonicalName, "JobInfoContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
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
        dialog.show(activity.supportFragmentManager, "income_dialog")
    }

    override fun enableAllTexts(isEnables: Boolean) {
        jobText.isEnabled = isEnables
        incomeText.isEnabled = isEnables
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(context) { _,_ ->
            navigation?.backFromJobInfo()
        }
    }

    override fun showInternalError() {
        alertHelper.internalError(context)
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack() {
        navigation?.backFromJobInfo()
    }

    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount): JobInfoFragment {
            val frag = JobInfoFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}