package com.fintechplatform.android.profile.address.ui

import com.fintechplatform.android.iban.models.UserResidential
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.net.NetHelper
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.mukesh.countrypicker.Country
import javax.inject.Inject

class AddressPresenter @Inject constructor(val view: AddressContract.View,
                                           val api: ProfileAPI,
                                           val configuration: DataAccount,
                                           val usersPersistanceDB: UsersPersistanceDB) : AddressContract.Presenter {

    override fun initializate() {

        val userProfile = usersPersistanceDB.residential(configuration.ownerId)
        userProfile?.let {
            view.setAddressText(it.address?: "")
            view.setPostalCodeText(it.ZIPcode?: "")
            view.setCityText(it.city?: "")
            view.setResidenceCountry(it.countryofresidence?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(configuration.accessToken,
                configuration.ownerId, configuration.tenantId){ profile, exception ->

            if (exception != null){
                handleErrors(exception)
                return@searchUser
            }

            if(profile == null){
                return@searchUser
            }

            val res = UserResidential(
                    profile.userid,
                    configuration.tenantId,
                    profile.address,
                    profile.ZIPcode,
                    profile.city,
                    profile.countryofresidence)

            usersPersistanceDB.saveResidential(res)

            view.enableAllTexts(true)

            initializate()

            view.setBackwardText()
            view.enableConfirmButton(false)
        }
    }

    override fun refreshConfirmButton() {
        if (view.getAddressText().isNotBlank() &&
                view.getCityText().isNotBlank() &&
                view.getPostalCodeText().isNotBlank() &&
                view.getResidenceCountry().isNotBlank()){

            view.setAbortText()
            view.enableConfirmButton(true)

        } else {
            view.enableConfirmButton(false)
        }

    }

    override fun onAbort() {
        view.goBack()
    }

    override fun onConfirm() {
        view.hideKeyboard()
        view.showWaiting()

        val residential = UserResidential(
                configuration.ownerId,
                configuration.tenantId,
                view.getAddressText(),
                view.getPostalCodeText(),
                view.getCityText(),
                view.getResidenceCountry()
        )

        api.residential(configuration.accessToken,
                residential) { optuserprofilereply, opterror ->

            view.enableConfirmButton(false)
            view.hideWaiting()

            if (opterror != null) {
                handleErrors(opterror)
                return@residential
            }

            if (optuserprofilereply?.userid == null) {
                return@residential
            }

            val userId = optuserprofilereply.userid as String
            val res = UserResidential(
                    userId,
                    configuration.tenantId,
                    view.getAddressText(),
                    view.getPostalCodeText(),
                    view.getCityText(),
                    view.getResidenceCountry())

            usersPersistanceDB.saveResidential(res)
            view.goBack()
        }
    }

    override fun onCountryOfResidenceClick() {
        view.showCountryPicker()
    }

    override fun onCountrySelected(name: String, code: String) {
        view.setResidenceCountry(code)
        refreshConfirmButton()
        view.closeCountryPicker()
    }

    fun convertCountry(name: String): String? {
        val countries = Country.getAllCountries()
        val matches = countries.filter { country ->
            country.name == name
        }
        return matches.singleOrNull()?.code
    }

    fun parseCountry(code: String): String? {
        val countries = Country.getAllCountries()
        val matches = countries.filter { country ->
            country.code == code
        }
        return matches.singleOrNull()?.name
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                return
        }
    }
}