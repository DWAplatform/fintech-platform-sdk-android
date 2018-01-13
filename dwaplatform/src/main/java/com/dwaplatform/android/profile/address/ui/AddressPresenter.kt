package com.dwaplatform.android.profile.address.ui

import com.dwaplatform.android.iban.models.UserResidential
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.profile.api.ProfileAPI
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB
import com.mukesh.countrypicker.Country
import javax.inject.Inject

class AddressPresenter @Inject constructor(val view: AddressContract.View,
                                           val api: ProfileAPI,
                                           val configuration: DataAccount,
                                           val usersPersistanceDB: UsersPersistanceDB) : AddressContract.Presenter {

    var token:String?=null

    override fun initializate() {

        val userProfile = usersPersistanceDB.residential(configuration.userId)
        userProfile?.let {
            view.setAddressText(it.address?: "")
            view.setPostalCodeText(it.ZIPcode?: "")
            view.setCityText(it.city?: "")
            view.setResidenceCountry(it.countryofresidence?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(token!!,
                configuration.userId){ profile, exception ->

            if (exception != null){
                return@searchUser
            }

            if(profile == null){
                return@searchUser
            }

            val res = UserResidential(
                    profile.userid,
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
                configuration.userId,
                view.getAddressText(),
                view.getPostalCodeText(),
                view.getCityText(),
                view.getResidenceCountry()
        )

        api.residential(token!!,
                residential) { optuserprofilereply, opterror ->

            view.enableConfirmButton(false)
            view.hideWaiting()

            if (opterror != null) {
                view.showCommunicationInternalNetwork()
                return@residential
            }

            if (optuserprofilereply?.userid == null) {
                view.showCommunicationInternalNetwork()
                return@residential
            }

            val res = UserResidential(
                    optuserprofilereply.userid,
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
}