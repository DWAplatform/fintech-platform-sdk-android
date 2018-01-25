package com.dwaplatform.android.profile.lightdata.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.dwaplatform.android.R
import com.dwaplatform.android.alert.AlertHelpers
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_lightdata.*
import java.util.*
import javax.inject.Inject

/**
 * Shows personal informations
 */
class LightDataActivity : AppCompatActivity(), LightDataContract.View {

    @Inject lateinit var presenter: LightDataContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LightDataUI.instance.createLightDataViewComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_lightdata)

        presenter.initialize()

        nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        surnameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        nationalityText.setOnClickListener {
            val picker = CountryPicker.newInstance("Seleziona nazionalità")

            picker.setListener { name, code, dialCode, flagDrawableResID ->
                presenter.onPickCountryNationality(name, code)
                picker.dismiss()
            }

            picker.show(supportFragmentManager, "COUNTRY_PICKER")
        }

        val dateListener = DatePickerDialog.OnDateSetListener { x, year, monthOfYear, dayOfMonth ->
            presenter.onPickBirthdayDate(year, monthOfYear, dayOfMonth)
        }

        birthdayText.setOnClickListener {
            val date = Calendar.getInstance()
            date.add(Calendar.YEAR, -18)

            showCalendar(date, dateListener)
        }

        abortButton.setOnClickListener { presenter.onAbortClick() }
    }

    override fun onBackPressed() {
        presenter.onAbortClick()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

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

    fun showCalendar(date: Calendar, dateListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(this, dateListener, date
                .get(Calendar.YEAR), date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showTokenExpired() {
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
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

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
}