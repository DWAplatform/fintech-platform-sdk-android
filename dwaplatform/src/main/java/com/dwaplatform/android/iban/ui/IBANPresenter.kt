package com.dwaplatform.android.iban.ui

import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.iban.api.IbanAPI
import com.dwaplatform.android.iban.db.IbanPersistanceDB
import com.dwaplatform.android.iban.models.BankAccount
import com.dwaplatform.android.iban.models.UserResidential
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB
import com.mukesh.countrypicker.Country
import javax.inject.Inject

class IBANPresenter @Inject constructor(val view: IBANContract.View,
                                        val api: IbanAPI,
                                        val configuration: DataAccount,
                                        val ibanPersistanceDB: IbanPersistanceDB,
                                        val usersPersistanceDB: UsersPersistanceDB): IBANContract.Presenter {

    private var countryofresidenceCode: String? = null

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
        usersPersistanceDB.residential(configuration.userId).let {
            view.setIBANText(calcIBANValue() ?: "")
            view.setAddressText(it?.address ?: "")
            view.setZipcodeText(it?.ZIPcode ?: "")
            view.setCityText(it?.city ?: "")
            view.setCountryofresidenceText(it?.countryofresidence ?: "")
            countryofresidenceCode = it?.countryofresidence
        }

        ibanPersistanceDB.load()?.let {
            view.setNumberText(it.iban?:"")
        }?: initBankAccount(configuration.userId)
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

        api.residenceProfile(
                configuration.accessToken,
                configuration.userId,
                countryofresidence = countryofresidenceCode,
                address = view.getAddressText(),
                zipcode = view.getZipcodeText(),
                city = view.getCityText()) { optuserprofilereply, opterror ->

            view.confirmButtonEnable(true)
            view.hideCommunicationWait()

            if (opterror != null) {
                handleErrors(opterror)
                return@residenceProfile
            }

            if (optuserprofilereply?.userid == null) {
                view.showCommunicationInternalError()
                return@residenceProfile
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

    fun initBankAccount(userid: String) {
        ibanPersistanceDB.delete()

        api.getbankAccounts(configuration.accessToken, userid) { optbankaccounts, opterror ->

            if (opterror != null) {
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

            initIBAN()
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

}