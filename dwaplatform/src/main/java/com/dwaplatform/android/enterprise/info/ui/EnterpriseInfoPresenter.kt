package com.dwaplatform.android.enterprise.info.ui

import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.enterprise.api.EnterpriseProfileAPI
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB
import com.dwaplatform.android.enterprise.models.EnterpriseInfo
import com.dwaplatform.android.models.DataAccount
import javax.inject.Inject

class EnterpriseInfoPresenter @Inject constructor(val view: EnterpriseInfoContract.View,
                                                  val api: EnterpriseProfileAPI,
                                                  val configuration: DataAccount,
                                                  val enterprisePersistanceDB: EnterprisePersistanceDB): EnterpriseInfoContract.Presenter {


    override fun initialize() {

        val enterpriseProfile = enterprisePersistanceDB.enterpriseProfile(configuration.userId)
        enterpriseProfile?.let {
            view.setNameText(enterpriseProfile.name?: "")
            view.setEnterpriseType(enterpriseProfile.enterpriseType?: "")
        }

    }

    override fun onConfirm() {
        view.showWaiting()
        view.hideKeyboard()

        val info = EnterpriseInfo(
                configuration.userId,
                view.getNameText(),
                view.getEnterpriseType())

        api.info(
                configuration.accessToken,
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
                    configuration.userId,
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
                 configuration.userId){ enterprise, exception ->

            if (exception != null){
                handleErrors(exception)
                return@getEnterprise
            }

            if (enterprise == null){
                return@getEnterprise
            }

            view.enableAllTexts(true)

            val enterpriseInfo = EnterpriseInfo(
                    configuration.userId,
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
}