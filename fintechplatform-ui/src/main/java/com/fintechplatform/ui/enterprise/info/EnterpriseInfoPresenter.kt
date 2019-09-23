package com.fintechplatform.ui.enterprise.info

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseInfo
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.models.DataAccount
import javax.inject.Inject

class EnterpriseInfoPresenter @Inject constructor(val view: EnterpriseInfoContract.View,
                                                  val api: EnterpriseAPI,
                                                  val configuration: DataAccount,
                                                  val enterprisePersistanceDB: EnterprisePersistanceDB): EnterpriseInfoContract.Presenter {


    override fun initialize() {

        val enterpriseProfile = enterprisePersistanceDB.enterpriseProfile(configuration.ownerId)
        enterpriseProfile?.let {
            view.setNameText(enterpriseProfile.name?: "")
            view.setEnterpriseType(selectOrganizationType(enterpriseProfile.enterpriseType?: ""))
        }

    }

    override fun onConfirm() {
        view.showWaiting()
        view.hideKeyboard()

        val info = EnterpriseInfo(configuration.ownerId,
                configuration.accountId,
                configuration.tenantId,
                view.getNameText(),
                onPickOrganizationType())

        api.info(configuration.accessToken,
                info){ enterpriseReply, exception ->

            if (exception != null){
                view.hideWaiting()
                handleErrors(exception)
                return@info
            }

            if (enterpriseReply == null) {
                return@info
            }

            view.enableConfirmButton(false)
            view.hideWaiting()

            val infoEnterprise = EnterpriseInfo(
                    configuration.ownerId,
                    view.getNameText(),
                    view.getEnterpriseType())

            enterprisePersistanceDB.saveInfo(infoEnterprise)

            onAbortClick()
        }
    }

    override fun refreshConfirmButton() {
        if (view.getNameText().isNotBlank() &&
                view.getEnterpriseType().isNotBlank()) {

            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
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

            if (enterprise == null){
                return@getEnterprise
            }

            view.enableAllTexts(true)

            val enterpriseInfo = EnterpriseInfo(configuration.ownerId,
                    configuration.accountId,
                    configuration.tenantId,
                    enterprise.name,
                    enterprise.enterpriseType)

            enterprisePersistanceDB.saveInfo(enterpriseInfo)
            initialize()
            view.setBackwardText()
            view.enableConfirmButton(false)
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

    private fun onPickOrganizationType(): String {
        when (view.getEnterpriseType()) {
            "Impresa individuale" -> return "SOLETRADER"
            "Azienda" -> return "BUSINESS"
            "Associazione" -> return "ORGANIZATION"
        }

        return ""
    }

    private fun selectOrganizationType(type: String): String {
        when (type) {
            "SOLETRADER" -> return "Impresa individuale"
            "BUSINESS" -> return "Azienda"
            "ORGANIZATION" -> return "Associazione"
        }

        return ""
    }
}