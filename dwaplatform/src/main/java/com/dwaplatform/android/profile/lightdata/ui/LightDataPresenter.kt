package com.dwaplatform.android.profile.lightdata.ui

import com.dwaplatform.android.models.DataAccount
import com.mukesh.countrypicker.Country
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 09/01/18.
 */
class LightDataPresenter @Inject constructor(val view: LightDataContract.View,
                                             //val api: DWApayAPI,
                                             val configuration: DataAccount): LightDataContract.Presenter {


    private var birthdayDate: String? = null
/*
    override fun initialize() {

        val userProfile = dbUsersHelper.userProfile()
        userProfile?.let {
            view.setNameText(userProfile.name?: "")
            view.setSurnameTect(userProfile.surname?: "")
            view.setBirthdayText(convertBirthday(userProfile.dateOfBirth?: "")?: "")
            view.setNationalityText(userProfile.nationality?: "")
        }

    }

    override fun onConfirm() {
        view.showWaiting()
        view.hideKeyboard()

        api.profile(userid = dbUsersHelper.userid(),
                name = view.getNameText(),
                surname = view.getSurnameText(),
                nationality = view.getNationalityText(),
                birthday = birthdayDate){ userProfileReply, exception ->

            if (exception != null){
                view.hideWaiting()
                view.showCommunicationInternalNetwork()
                return@profile
            }

            if (userProfileReply?.userid == null) {
                view.hideWaiting()
                view.showCommunicationInternalNetwork()
                return@profile
            }

            view.enableConfirmButton(false)
            view.hideWaiting()

            val userProfile = UserLightData(
                    view.getNameText(),
                    view.getSurnameText(),
                    birthdayDate,
                    view.getNationalityText())

            dbUsersHelper.saveUserLightData(userProfile)
            onAbortClick()
        }
    }

    override fun refreshConfirmButton() {
        if (view.getNameText().isNotBlank() &&
                view.getSurnameText().isNotBlank() &&
                view.getDateOfBirthText().isNotBlank() &&
                view.getNationalityText().isNotBlank()) {

            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }

    override fun onPickBirthdayDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val b = Calendar.getInstance()
        b.set(Calendar.YEAR, year)
        b.set(Calendar.MONTH, monthOfYear)
        b.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val birthday = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(b.time)
        view.setBirthdayText(birthday)
        val convertedBirthday = SimpleDateFormat("yyyy-MM-dd", Locale.ITALY).format(b.time)
        birthdayDate = convertedBirthday
        refreshConfirmButton()
    }

    override fun onPickCountryNationality(name: String, code: String) {
        view.setNationalityText(code)
        refreshConfirmButton()
    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun onRefresh() {

        view.enableAllTexts(false)

        api.searchUser(dbUsersHelper.userid()){ profile, exception ->

            if (exception != null){
                return@searchUser
            }

            if (profile == null){
                return@searchUser
            }

            view.enableAllTexts(true)

            val userlightdata = UserLightData(
                    profile.name,
                    profile.surname,
                    profile.dateOfBirth,
                    profile.nationality)

            dbUsersHelper.saveUserLightData(userlightdata)
            initialize()
            view.setBackwardText()
            view.enableConfirmButton(false)
        }
    }

    private fun parseCountryCode(code: String): String? {
        val countries = Country.getAllCountries()
        val matches = countries.filter { country ->
            country.code == code
        }
        return matches.singleOrNull()?.name
    }

    private fun convertCountry(name: String?): String? {
        val countries = Country.getAllCountries()
        val matches = countries.filter { country ->
            country.name == name
        }
        return matches.singleOrNull()?.code
    }

    private fun convertBirthday(birthday: String?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        if(birthday.isNullOrBlank()){
            return ""
        } else {
            val d = sdf.parse(birthday)
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            return formatter.format(d)
        }
    }*/

    override fun onConfirm() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initialize() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshConfirmButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPickBirthdayDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPickCountryNationality(name: String, code: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAbortClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}