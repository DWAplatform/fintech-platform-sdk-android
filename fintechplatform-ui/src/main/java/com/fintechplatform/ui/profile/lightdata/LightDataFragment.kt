package com.fintechplatform.ui.profile.lightdata

import android.app.DatePickerDialog
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
import com.fintechplatform.ui.profile.lightdata.di.LightDataViewComponent
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_lightdata.*
import kotlinx.android.synthetic.main.activity_lightdata.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


open class LightDataFragment: Fragment(), LightDataContract.View {

    @Inject
    lateinit var presenter: LightDataContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var navigation: LightDataContract.Navigation? = null

    open fun createLightDataViewComponent(context: Context, view: LightDataContract.View, hostname: String, dataAccount: DataAccount): LightDataViewComponent {
        return LightDataUI.Builder.buildLightDataViewComponent(context, view, hostname, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_lightdata, container, false)

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createLightDataViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.surnameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        view.nationalityText.setOnClickListener {
            val picker = CountryPicker.newInstance("Seleziona nazionalità")

            picker.setListener { name, code, dialCode, flagDrawableResID ->
                presenter.onPickCountryNationality(name, code)
                picker.dismiss()
            }

            picker.show(activity.supportFragmentManager, "COUNTRY_PICKER")
        }

        val dateListener = DatePickerDialog.OnDateSetListener { x, year, monthOfYear, dayOfMonth ->
            presenter.onPickBirthdayDate(year, monthOfYear, dayOfMonth)
        }

        view.birthdayText.setOnClickListener {
            showCalendar(dateListener)
        }

        view.abortButton.setOnClickListener { presenter.onAbortClick() }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initialize()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? LightDataContract.Navigation)?.let {
            navigation = it
        }?: Log.e(LightDataFragment::class.java.canonicalName, "LightDataContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    /** Contract.View */

    override fun setBackwardText() {
        abortButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        abortButton.text = resources.getString(R.string.abort)
    }

    override fun enableAllTexts(areEnables: Boolean) {
        nameText.isEnabled = areEnables
        surnameText.isEnabled = areEnables
        birthdayText.isEnabled = areEnables
        nationalityText.isEnabled = areEnables
    }

    override fun getNameText(): String {
        return nameText.text.toString()
    }

    override fun getSurnameText(): String {
        return surnameText.text.toString()
    }

    override fun getNationalityText(): String {
        return nationalityText.text.toString()
    }

    override fun getDateOfBirthText(): String {
        return birthdayText.text.toString()
    }

    override fun setNameText(name: String){
        nameText.setText(name)
    }

    override fun setSurnameTect(surname: String) {
        surnameText.setText(surname)
    }

    override fun setBirthdayText(birthday: String) {
        birthdayText.setText(birthday)
    }

    override fun setNationalityText(nationality: String) {
        nationalityText.setText(nationality)
    }

    private fun showCalendar(dateListener: DatePickerDialog.OnDateSetListener) {

        val date = Calendar.getInstance()
        if(getDateOfBirthText().isBlank()) {
            date.add(Calendar.YEAR, -18)
        } else {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val datef = sdf.parse(getDateOfBirthText())
            date.time = datef
        }

        DatePickerDialog(context, dateListener,
                date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showTokenExpired() {
        alertHelper.tokenExpired(context) { _, _ ->
            navigation?.backFromLightData()
        }
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack() {
        navigation?.backFromLightData()
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    companion object{
        fun newInstance(hostName: String, dataAccount: DataAccount): LightDataFragment {
            val frag = LightDataFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}