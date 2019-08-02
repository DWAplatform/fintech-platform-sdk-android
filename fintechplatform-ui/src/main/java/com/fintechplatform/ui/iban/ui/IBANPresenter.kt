package com.fintechplatform.ui.iban.ui

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseAddress
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.api.iban.models.BankAccount
import com.fintechplatform.api.iban.models.UserResidential
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.iban.db.IbanPersistanceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import com.mukesh.countrypicker.Country
import java.util.*
import javax.inject.Inject

class IBANPresenter @Inject constructor(
                                        val api: IbanAPI,
                                        val apiProfile: ProfileAPI,
                                        val apiEnterprise: EnterpriseAPI,
                                        val configuration: DataAccount,
                                        val ibanPersistanceDB: IbanPersistanceDB,
                                        val usersPersistanceDB: UsersPersistanceDB,
                                        val enterprisePersistanceDB: EnterprisePersistanceDB): IBANContract.Presenter {

    private var countryofresidenceCode: String? = null

    private var ibanServerCalled: Boolean = false
    private var residentialServerCalled: Boolean = false
    private var idempotencyCashOut: String? = null

    lateinit var view: IBANContract.View

    override fun init(view: IBANContract.View) {
        this.view = view
        idempotencyCashOut = UUID.randomUUID().toString()
        loadAllFromDB()
        refreshAllFromServer()
        view.setBackwardText()
    }

    fun loadAllFromDB() {
        loadResidentialFromDB()
        loadIBANFromDB()
    }

    fun loadResidentialFromDB() {
        when (configuration.accountType) {
            "PERSONAL" -> usersPersistanceDB.residential(configuration.ownerId)?.let {
                view.setAddressText(it.address ?: "")
                view.setZipcodeText(it.ZIPcode ?: "")
                view.setCityText(it.city ?: "")
                view.setCountryofresidenceText(it.countryofresidence ?: "")
                countryofresidenceCode = it.countryofresidence
            }
            "BUSINESS" -> {
                enterprisePersistanceDB.enterpriseProfile(configuration.ownerId)?.let {
                    view.setAddressText(it.addressOfHeadquarters ?: "")
                    view.setZipcodeText(it.postalCodeHeadquarters ?: "")
                    view.setCityText(it.cityOfHeadquarters ?: "")
                    view.setCountryofresidenceText(it.countryHeadquarters ?: "")
                    countryofresidenceCode = it.countryHeadquarters
                }
                refreshConfirmButton()
            }
        }
    }

    fun loadIBANFromDB() {
        ibanPersistanceDB.load(configuration.accountId)?.let {
            view.setNumberText(it.iban?:"")
        }
        refreshConfirmButton()
    }

    override fun refreshConfirmButton() {
        view.setAbortText()
        val isEnabled = view.getNumberTextLength() >= 20 &&
                view.getAddressTextLength() > 0 &&
                view.getZipcodeTextLength() > 0 &&
                view.getCityTextLength() > 0 &&
                view.getCountryofresidenceTextLength() > 0

        view.confirmButtonEnable(isEnabled)
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

        when (configuration.accountType) {
            "PERSONAL" -> {
                val residential = UserResidential(configuration.ownerId, configuration.tenantId, view.getAddressText(),  view.getCityText(), view.getZipcodeText(), countryofresidenceCode)

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

                    val userId = optuserprofilereply.userid as String
                    val res = UserResidential(
                            userId,
                            configuration.tenantId,
                            view.getAddressText(),
                            view.getZipcodeText(),
                            view.getCityText(),
                            countryofresidenceCode)

                    usersPersistanceDB.saveResidential(res)

                    saveIban()
                }
            }

            "BUSINESS" -> {
                val address = EnterpriseAddress(configuration.ownerId, configuration.accountId, configuration.tenantId,
                        view.getAddressText(),
                        view.getCityText(),
                        view.getZipcodeText(),
                        countryofresidenceCode)
                apiEnterprise.address(configuration.accessToken, address) { enterpriseProfile, exception ->
                    view.confirmButtonEnable(true)
                    view.hideCommunicationWait()

                    if (exception != null) {
                        handleErrors(exception)
                        return@address
                    }

                    if (enterpriseProfile == null) {
                        view.showCommunicationInternalError()
                        return@address
                    }

                    val adres = EnterpriseAddress(
                            enterpriseProfile.enterpriseId,
                            configuration.accountId,
                            configuration.tenantId,
                            view.getAddressText(),
                            view.getCityText(),
                            view.getZipcodeText(),
                            countryofresidenceCode)

                    enterprisePersistanceDB.saveAddress(adres)

                    saveIban()
                }
            }
        }

    }

    fun saveIban() {
        view.confirmButtonEnable(false)
        view.showCommunicationWait()

        val idempotencyCashOut = this.idempotencyCashOut ?: return

        api.createLinkedBank(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId,
                view.getNumberText(),
                idempotencyCashOut) { optbankaccount, opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@createLinkedBank
            }

            if (optbankaccount == null) {
                view.showCommunicationInternalError()
                return@createLinkedBank
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
        val optiban = ibanPersistanceDB.load(configuration.accountId)
        return optiban?.iban
    }


    fun refreshAllFromServer() {
        refreshFromServerBankAccount()
        refreshFromServerResidential()
    }

    fun refreshFromServerBankAccount() {
        ibanServerCalled = false
        view.enableAllTexts(false)

        api.getLinkedBanks(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId) { optbankaccounts, opterror ->

            ibanServerCalled = true

            if (opterror != null) {
                handleErrors(opterror)
                return@getLinkedBanks
            }
            if (optbankaccounts == null) {
                return@getLinkedBanks
            }
            val bankaccounts = optbankaccounts
            bankaccounts.forEach { ba ->
                val iban = BankAccount(ba.bankaccountid, ba.accountId, ba.iban, ba.activestate)
                ibanPersistanceDB.replace(iban)
            }

            loadIBANFromDB()

            finishServerCalls()

        }
    }

    private fun refreshFromServerResidential() {
        residentialServerCalled = false
        view.enableAllTexts(false)

        when(configuration.accountType) {
            "PERSONAL" -> {
                apiProfile.searchUser(configuration.accessToken,
                    configuration.ownerId, configuration.tenantId) { profile, exception ->

                residentialServerCalled = true

                if (exception != null) {
                    handleErrors(exception)
                    return@searchUser
                }

                if (profile == null) {
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


                loadResidentialFromDB()

                finishServerCalls()
                }
            }

            "BUSINESS" -> {
                apiEnterprise.getEnterprise(configuration.accessToken, configuration.ownerId, configuration.tenantId) { enterpriseProfile, exception ->
                    residentialServerCalled = true

                    if (exception != null) {
                        handleErrors(exception)
                        return@getEnterprise
                    }

                    if (enterpriseProfile == null) {
                        return@getEnterprise
                    }

                    val profile = enterpriseProfile
                    val address = EnterpriseAddress(
                            configuration.ownerId,
                            configuration.accountId,
                            configuration.tenantId,
                            profile.addressOfHeadquarters,
                            profile.cityOfHeadquarters,
                            profile.postalCodeHeadquarters,
                            profile.countryHeadquarters
                    )

                    enterprisePersistanceDB.saveAddress(address)
                    loadResidentialFromDB()

                    finishServerCalls()
                }
            }
        }
    }



    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            is NetHelper.UserNotFound -> return
            is NetHelper.EnterpriseNotFound -> return
            else ->
                view.showCommunicationInternalError()
        }
    }

    private fun finishServerCalls() {
        if(!ibanServerCalled || !residentialServerCalled) {
            return
        }
        refreshConfirmButton()
        view.enableAllTexts(true)
        view.setBackwardText()
    }

}