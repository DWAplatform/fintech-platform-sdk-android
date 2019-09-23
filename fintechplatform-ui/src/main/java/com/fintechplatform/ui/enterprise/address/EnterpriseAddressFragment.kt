package com.fintechplatform.ui.enterprise.address

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.enterprise.address.di.EnterpriseAddressViewComponent
import com.fintechplatform.ui.models.DataAccount
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_profile_address.*
import kotlinx.android.synthetic.main.fragment_enterprise_address.view.*
import javax.inject.Inject


open class EnterpriseAddressFragment: Fragment(), EnterpriseAddressContract.View {

    @Inject
    lateinit var presenter: EnterpriseAddressContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var picker: CountryPicker? = null
    var navigation: EnterpriseAddressContract.Navigation? = null

    open fun createEnterpriseAddressViewComponent(context: Context, view: EnterpriseAddressContract.View, hostName: String, dataAccount: DataAccount): EnterpriseAddressViewComponent {
        return EnterpriseAddressUI.Builder.buildAddressComponent(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_enterprise_address, container, false)

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createEnterpriseAddressViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.addressText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.zipcodeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.cityText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.countryofresidenceText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.countryofresidenceText.setOnClickListener { presenter.onCountryOfResidenceClick()}

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initializate()
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

    override fun getAddressText(): String {
        return addressText.text.toString()
    }

    override fun getPostalCodeText(): String {
        return zipcodeText.text.toString()
    }

    override fun getCityText(): String {
        return cityText.text.toString()
    }

    override fun getResidenceCountry(): String {
        return countryofresidenceText.text.toString()
    }

    override fun setAddressText(address: String) {
        addressText.setText(address)
    }

    override fun setPostalCodeText(zipcode: String) {
        zipcodeText.setText(zipcode)
    }

    override fun setCityText(city: String) {
        cityText.setText(city)
    }

    override fun setResidenceCountry(country: String) {
        countryofresidenceText.setText(country)
    }

    override fun showCountryPicker(){
        picker = CountryPicker.newInstance("Seleziona lo stato")

        picker?.setListener { name, code, dialCode, flagDrawableResID ->
            presenter.onCountrySelected(name, code)
        }
        picker?.show(activity.supportFragmentManager, "COUNTRY_PICKER")
    }

    override fun closeCountryPicker(){
        picker?.dismiss()
    }

    override fun enableAllTexts(isEnables: Boolean) {
        addressText.isEnabled = isEnables
        zipcodeText.isEnabled = isEnables
        cityText.isEnabled = isEnables
        countryofresidenceText.isEnabled = isEnables
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(context) { _,_ ->
            navigation?.backFromEnterpriseAddress()
        }
    }

    override fun showCommunicationInternalError() {
        alertHelper.internalError(context).show()
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        navigation?.backFromEnterpriseAddress()
    }

    companion object{
        fun newInstance(hostName: String, dataAccount: DataAccount): EnterpriseAddressFragment {
            val frag = EnterpriseAddressFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}