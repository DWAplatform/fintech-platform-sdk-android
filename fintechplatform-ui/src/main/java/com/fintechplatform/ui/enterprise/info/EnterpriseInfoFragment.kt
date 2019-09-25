package com.fintechplatform.ui.enterprise.info

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
import com.fintechplatform.ui.enterprise.info.di.EnterpriseInfoViewComponent
import com.fintechplatform.ui.enterprise.info.dialog.OrganizationTypeDialog
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.fragment_infoenterprise.*
import kotlinx.android.synthetic.main.fragment_infoenterprise.view.*
import javax.inject.Inject


open class EnterpriseInfoFragment: Fragment(), EnterpriseInfoContract.View, OrganizationTypeDialog.BusinessTypePicker {

    @Inject
    lateinit var presenter: EnterpriseInfoContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var navigation: EnterpriseInfoContract.Navigation? = null

    open fun createEnterpriseInfoViewComponent(context: Context, view: EnterpriseInfoContract.View, hostName: String, dataAccount: DataAccount): EnterpriseInfoViewComponent {
        return EnterpriseInfoUI.Builder.buildLightDataViewComponent(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_infoenterprise, container, false)

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createEnterpriseInfoViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }
        view.nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.enterpriseTypeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        view.enterpriseTypeText.setOnClickListener {
            showOrganizatoinTypeDialog()
        }

        view.abortButton.setOnClickListener { presenter.onAbortClick() }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initialize()
    }

    fun showOrganizatoinTypeDialog() {
        val dialog = OrganizationTypeDialog.newInstance()
//        dialog.setTargetFragment(supportFragmentManager, DIALOG_REQUEST_CODE)
        dialog.show(activity.supportFragmentManager, DIALOG_REQUEST)
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? EnterpriseInfoContract.Navigation)?.let {
            navigation = it
        }?: Log.e(EnterpriseInfoFragment::class.java.canonicalName, "EnterpriseInfoContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun setBackwardText() {
        abortButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        abortButton.text = resources.getString(R.string.abort)
    }

    override fun enableAllTexts(areEnables: Boolean) {
        nameText.isEnabled = areEnables
        enterpriseTypeText.isEnabled = areEnables
    }

    override fun getNameText(): String {
        return nameText.text.toString()
    }

    override fun getEnterpriseType(): String {
        return enterpriseTypeText.text.toString()
    }

    override fun setNameText(name: String){
        nameText.setText(name)
    }

    override fun setEnterpriseType(surname: String) {
        enterpriseTypeText.setText(surname)
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showTokenExpired() {
        alertHelper.tokenExpired(context) { _, _ ->
            navigation?.backFromBusinessInfo()
        }
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack() {
        navigation?.backFromBusinessInfo()
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onPickBusinessType(businessyType: String) {
        setEnterpriseType(businessyType)
    }

    companion object {
        const val DIALOG_REQUEST = "organization_type"

        fun newInstance(hostName: String, dataAccount: DataAccount): EnterpriseInfoFragment {
            val frag = EnterpriseInfoFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}