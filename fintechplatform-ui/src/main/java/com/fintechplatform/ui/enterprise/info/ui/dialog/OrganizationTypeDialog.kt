package com.fintechplatform.ui.enterprise.info.ui.dialog

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.fintechplatform.ui.R
import com.fintechplatform.ui.enterprise.info.ui.EnterpriseInfoActivity


class OrganizationTypeDialog: DialogFragment() {

    var listener : BusinessTypePicker?=null

    interface BusinessTypePicker {
        fun onPickBusinessType(businessyType: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Tipo di organizzazione")
                .setItems(R.array.organization_types, { _, id ->

                    val resArray = resources.getStringArray(R.array.organization_types)
                    listener?.onPickBusinessType(resArray[id])
                    val intent = Intent()
                    intent.putExtra("organizationType", resArray[id])
                    activity.setResult(RESULT_OK, intent)
                    targetFragment?.onActivityResult(targetRequestCode, RESULT_OK, intent)
                    dismiss()
                })

        return builder.show()
    }

    companion object {
        fun newInstance(): OrganizationTypeDialog {
            return OrganizationTypeDialog()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is EnterpriseInfoActivity) {
            listener = context as BusinessTypePicker
        }
    }
}