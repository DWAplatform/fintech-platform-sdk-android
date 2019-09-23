package com.fintechplatform.ui.enterprise.address

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseAddress
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.models.DataAccount
import com.mukesh.countrypicker.Country
import javax.inject.Inject

class EnterpriseAddressPresenter @Inject constructor(val view: EnterpriseAddressContract.View,
                                                     val api: EnterpriseAPI,
                                                     val configuration: DataAccount,
                                                     val enterprisePersistanceDB: EnterprisePersistanceDB) : EnterpriseAddressContract.Presenter {

    override fun initializate() {

        val enterprise = enterprisePersistanceDB.enterpriseProfile(configuration.ownerId)
        enterprise?.let {
            view.setAddressText(it.addressOfHeadquarters?: "")
            view.setPostalCodeText(it.postalCodeHeadquarters?: "")
            view.setCityText(it.cityOfHeadquarters?: "")
            view.setResidenceCountry(it.countryHeadquarters?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.getEnterprise(configuration.accessToken,
                configuration.ownerId,
                configuration.tenantId){ enterprise, exception ->

            if (exception != null){
                handleErrors(exception)
                return@getEnterprise
            }

            if(enterprise == null){
                return@getEnterprise
            }

            val address = EnterpriseAddress(
                    configuration.ownerId,
                    configuration.accountId,
                    configuration.tenantId,
                    enterprise.addressOfHeadquarters,
                    enterprise.cityOfHeadquarters,
                    enterprise.postalCodeHeadquarters,
                    enterprise.countryHeadquarters)

            enterprisePersistanceDB.saveAddress(address)

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

        val residential = EnterpriseAddress(
                configuration.ownerId,
                configuration.accountId,
                configuration.tenantId,
                view.getAddressText(),
                view.getCityText(),
                view.getPostalCodeText(),
                view.getResidenceCountry()
        )

        api.address(configuration.accessToken,
                residential) { optenterprise, opterror ->

            view.enableConfirmButton(false)
            view.hideWaiting()

            if (opterror != null) {
                handleErrors(opterror)
                return@address
            }

            if (optenterprise == null) {
                return@address
            }

            val address = EnterpriseAddress(
                    optenterprise.enterpriseId,
                    configuration.accountId,
                    configuration.tenantId,
                    view.getAddressText(),
                    view.getCityText(),
                    view.getPostalCodeText(),
                    view.getResidenceCountry())

            enterprisePersistanceDB.saveAddress(address)
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