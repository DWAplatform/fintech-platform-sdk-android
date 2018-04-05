package com.fintechplatform.android.profile.lightdata.ui

import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.net.NetHelper
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.fintechplatform.android.profile.models.UserLightData
import com.mukesh.countrypicker.Country
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LightDataPresenter @Inject constructor(val view: LightDataContract.View,
                                             val api: ProfileAPI,
                                             val configuration: DataAccount,
                                             val usersPersistanceDB: UsersPersistanceDB): LightDataContract.Presenter {


    private var birthdayDate: String? = null
    var token:String?=null

    override fun initialize() {

        val userProfile = usersPersistanceDB.userProfile(configuration.ownerId)
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

        val lightdata = UserLightData(
                configuration.ownerId,
                configuration.tenantId,
                view.getNameText(),
                view.getSurnameText(),
                birthdayDate,
                view.getNationalityText() )

        api.lightdata(
                configuration.accessToken,
                lightdata){ userProfileReply, exception ->

            if (exception != null){
                view.hideWaiting()
                handleErrors(exception)
                return@lightdata
            }

            if (userProfileReply?.userid == null) {
                return@lightdata
            }

            view.enableConfirmButton(false)
            view.hideWaiting()

            val userLightDataProfile = UserLightData(
                    configuration.ownerId,
                    view.getNameText(),
                    view.getSurnameText(),
                    birthdayDate,
                    view.getNationalityText())

            usersPersistanceDB.saveLightData(userLightDataProfile)

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

        api.searchUser(configuration.accessToken,
                 configuration.ownerId, configuration.tenantId){ profile, exception ->

            if (exception != null){
                handleErrors(exception)
                return@searchUser
            }

            if (profile == null){
                return@searchUser
            }

            view.enableAllTexts(true)

            val userlightdata = UserLightData(
                    configuration.ownerId,
                    configuration.tenantId,
                    profile.name,
                    profile.surname,
                    profile.dateOfBirth,
                    profile.nationality)

            usersPersistanceDB.saveLightData(userlightdata)
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
        if (birthday.isNullOrBlank()) {
            return ""
        } else {
            val d = sdf.parse(birthday)
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            return formatter.format(d)
        }
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpired()
            else ->
                return
        }
    }
}