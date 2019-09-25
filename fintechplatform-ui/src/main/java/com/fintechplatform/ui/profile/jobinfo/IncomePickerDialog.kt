package com.fintechplatform.ui.profile.jobinfo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R

class IncomePickerDialog: DialogFragment() {

    interface OnPickSalaryIncome {
        fun salaryPicked(income: String)
    }

    var listener: OnPickSalaryIncome?= null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.choose_salary))
                .setItems(R.array.salaries) { dialog, id ->

                    val resArray = resources.getStringArray(R.array.salaries)
                    listener?.salaryPicked(resArray[id])
                    dismiss()
                }
        return builder.show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val act = context as FragmentActivity 
        val frag = act.supportFragmentManager.findFragmentByTag(JobInfoFragment::class.java.canonicalName)

        if ((frag as JobInfoFragment).presenter is OnPickSalaryIncome){
            listener = frag.presenter as OnPickSalaryIncome
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance(): IncomePickerDialog {
            return IncomePickerDialog()
        }
    }
}