package com.dwaplatform.android.profile.jobinfo.ui

interface JobInfoContract {
    interface View {
        fun setAbortText()
        fun setBackwardText()
        fun getJobInfoText(): String
        fun getIncomeText(): String
        fun setJobInfoText(jobInfo: String)
        fun setIcomeText(income: String)
        fun showIncomeDialog()
        fun enableAllTexts(isEnables: Boolean)
        fun enableConfirmButton(isEnable: Boolean)
        fun showCommunicationInternalNetwork()
        fun showWaiting()
        fun hideWaiting()
        fun hideKeyboard()
        fun goBack()
    }

    interface Presenter {
        fun initializate(array: Array<String>)
        fun onRefresh()
        fun onAbort()
        fun onConfirm()
        fun refreshConfirmButton()
        fun onIncomeClick()
    }
}