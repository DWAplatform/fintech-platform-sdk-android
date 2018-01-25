package com.fintechplatform.android.iban.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.iban.api.IbanAPI
import com.fintechplatform.android.iban.db.IbanPersistanceDB
import com.fintechplatform.android.iban.models.BankAccount
import com.fintechplatform.android.iban.models.UserResidential
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.mukesh.countrypicker.Country
import javax.inject.Inject

class IBANPresenter @Inject constructor(val view: IBANContract.View,
                                        val api: IbanAPI,
                                        val apiProfile: ProfileAPI,
                                        val configuration: DataAccount,
                                        val ibanPersistanceDB: IbanPersistanceDB,
                                        val usersPersistanceDB: UsersPersistanceDB): IBANContract.Presenter {

    private var countryofresidenceCode: String? = null
    private var ibanPersistance: Boolean = false
    private var residentialPersistance: Boolean = false

    override fun refreshConfirmButton() {
        view.setAbortText()
        val isEnabled = view.getNumberTextLength() >= 20 &&
                view.getAddressTextLength() > 0 &&
                view.getZipcodeTextLength() > 0 &&
                view.getCityTextLength() > 0 &&
                view.getCountryofresidenceTextLength() > 0

        view.confirmButtonEnable(isEnabled)
    }

    override fun initIBAN() {

        usersPersistanceDB.residential(configuration.userId)?.let {
            view.setIBANText(calcIBANValue() ?: "")
            view.setAddressText(it.address ?: "")
            view.setZipcodeText(it.ZIPcode ?: "")
            view.setCityText(it.city ?: "")
            view.setCountryofresidenceText(it.countryofresidence ?: "")
            countryofresidenceCode = it.countryofresidence
            residentialPersistance = true
        }?: initResidential()

        ibanPersistanceDB.load()?.let {
            view.setNumberText(it.iban?:"")
            ibanPersistance = true
        }?: initBankAccount()
    }

    override fun onCountryOfResidenceClick() {
        view.showCountryPicker()
    }

    override fun onCountrySelected(name: String, code: String) {
        view.setCountryofresidenceText(code)
        countryofresidenceCode = code
        refreshConfirmButton()
        view.closeCountryPicker()
    }

    override fun onAbortClick(){
        view.hideSoftkeyboard()
        view.goBack()
    }

    override fun onConfirm() {
        view.confirmButtonEnable(false)
        view.showCommunicationWait()

        val residential = UserResidential(configuration.userId, view.getAddressText(),  view.getZipcodeText(), view.getCityText(), countryofresidenceCode)
        apiProfile.residential(
                configuration.accessToken,
                residential) { optuserprofilereply, opterror ->

            view.confirmButtonEnable(true)
            view.hideCommunicationWait()

            if (opterror != null) {
                handleErrors(opterror)
                return@residential
            }

            if (optuserprofilereply?.userid == null) {
                view.showCommunicationInternalError()
                return@residential
            }

            val res = UserResidential(
                    optuserprofilereply.userid,
                    view.getAddressText(),
                    view.getZipcodeText(),
                    view.getCityText(),
                    countryofresidenceCode)

            usersPersistanceDB.saveResidential(res)

            saveIban()
        }
    }

    fun saveIban() {
        view.confirmButtonEnable(false)
        view.showCommunicationWait()

        api.createIBAN(configuration.accessToken,
                configuration.userId,
                view.getNumberText()) { optbankaccount, opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@createIBAN
            }

            if (optbankaccount == null) {
                view.showCommunicationInternalError()
                return@createIBAN
            }
            ibanPersistanceDB.replace(optbankaccount)

            onAbortClick()
        }
    }

    fun parseCountry(code: String): String? {
        val countries = Country.getAllCountries()
        val matches = countries.filter { country ->
            country.code == code
        }
        return matches.singleOrNull()?.name
    }

    fun calcIBANValue(): String? {
        val optiban = ibanPersistanceDB.load()
        return optiban?.iban
    }

    fun initBankAccount() {
        ibanPersistanceDB.delete()

        api.getbankAccounts(configuration.accessToken, configuration.userId) { optbankaccounts, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getbankAccounts
            }
            if (optbankaccounts == null) {
                return@getbankAccounts
            }
            val bankaccounts = optbankaccounts
            bankaccounts.forEach { ba ->
                val iban = BankAccount(ba.bankaccountid, ba.iban, ba.activestate)
                ibanPersistanceDB.save(iban)
            }
            ibanPersistance = true
            refreshData()
        }
    }

    private fun initResidential() {
        view.enableAllTexts(false)

        apiProfile.searchUser(configuration.accessToken,
                configuration.userId){ profile, exception ->

            if (exception != null){
                handleErrors(exception)
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

            residentialPersistance = true
            refreshData()

            refreshConfirmButton()
            view.setBackwardText()
        }
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }

    private fun refreshData() {
        if(!ibanPersistance || !residentialPersistance)
            return
        initIBAN()
    }

}