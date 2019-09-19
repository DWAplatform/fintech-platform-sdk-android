package com.fintechplatform.ui.profile.address

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
import com.fintechplatform.ui.profile.address.di.AddressViewComponent
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_profile_address.*
import kotlinx.android.synthetic.main.activity_profile_address.view.*
import javax.inject.Inject


open class AddressFragment: Fragment(), AddressContract.View {

    @Inject
    lateinit var presenter: AddressContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var picker: CountryPicker? = null
    var navigation: AddressContract.Navigation? = null

    open fun createAddressComponent(context: Context, view: AddressContract.View, hostName: String, dataAccount: DataAccount): AddressViewComponent {
        return AddressUI.Builder.buildAddressComponent(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profile_address, container, false)
        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createAddressComponent(context, this, hostname, dataAccount).inject(this)
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? AddressContract.Navigation)?.let {
            navigation = it
        }?: Log.e(AddressFragment::class.java.canonicalName, "AddressContract.Navigation must be implemented in your Activity!!")
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
        picker = CountryPicker.newInstance("Seleziona stato di residenza")

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
            navigation?.goBackFromAddress()
        }
    }

    override fun showCommunicationInternalError() {
        alertHelper.internalError(context).show()
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        navigation?.goBackFromAddress()
    }


    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount): AddressFragment {
            val frag = AddressFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}