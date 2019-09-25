package com.fintechplatform.ui.enterprise.documents.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import com.fintechplatform.api.enterprise.models.EnterpriseDocType
import com.fintechplatform.ui.R
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsFragment


class EnterpriseDocTypeDialog: DialogFragment() {

    var listener: DocTypePicker? = null

    interface DocTypePicker {
        fun onPickDocType(docType: EnterpriseDocType)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Doc Types")
                .setItems(R.array.company_doc_types) { _, id ->

                    val resArray = resources.getStringArray(R.array.company_doc_types)
                    val typeList = resArray.map { 
                        EnterpriseDocType.valueOf(it)
                    }
                    listener?.onPickDocType(typeList[id])
                    val intent = Intent()
                    intent.putExtra("company_doctypes", resArray[id])
                    activity.setResult(Activity.RESULT_OK, intent)
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                }

        return builder.show()
    }

    companion object {
        fun newInstance(): EnterpriseDocTypeDialog {
            return EnterpriseDocTypeDialog()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val act = context as FragmentActivity
        val frag = act.supportFragmentManager.findFragmentByTag(EnterpriseDocumentsFragment::class.java.canonicalName)
        if ((frag as EnterpriseDocumentsFragment).presenter is DocTypePicker){
            listener = frag.presenter as DocTypePicker
        }
        
        
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
