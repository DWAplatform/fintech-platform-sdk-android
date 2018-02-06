package com.fintechplatform.android.card.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.api.PaymentCardAPI
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB
import com.fintechplatform.android.models.DataAccount
import javax.inject.Inject

class PaymentCardPresenter @Inject constructor(var view: PaymentCardContract.View,
                                               var api: PaymentCardAPI,
                                               var dataAccountHelper: DataAccount,
                                               val paymentCardpersistanceDB: PaymentCardPersistenceDB ): PaymentCardContract.Presenter {

    override fun refreshConfirmButton() {

        val isEnabled = view.getNumberTextLength() >= 16
                && view.getDateTextLength() >= 4
                && view.getCCvTextLength() >= 3
                && !dataAccountHelper.accessToken.isEmpty()

        view.confirmButtonEnable(isEnabled)
    }

    override fun initPaymentCard(){
        paymentCardpersistanceDB.deletePaymentCard()
        api.restAPI.getPaymentCards(dataAccountHelper.accessToken, dataAccountHelper.userId, dataAccountHelper.accountId, dataAccountHelper.tenantId){ optcards, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getPaymentCards
            }
            if (optcards == null) {
                return@getPaymentCards
            }
            val cards = optcards
            cards.forEach { c ->
                paymentCardpersistanceDB.savePaymentCard(c)
            }
        }

    }

    override fun onConfirm() {
        view.confirmButtonEnable(false)

        view.showCommunicationWait()

        // FIXME fix currency
        api.registerCard(dataAccountHelper.accessToken,
                dataAccountHelper.userId,
                dataAccountHelper.accountId,
                dataAccountHelper.tenantId,
                view.getNumberText(),
                view.getDAteText(),
                view.getCCvText(), "EUR") { optcard, opterror ->

            view.hideCommunicationWait()

            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@registerCard
            }

            if (optcard == null) {
                view.showCommunicationInternalError()
                return@registerCard
            }

            paymentCardpersistanceDB.replace(optcard)

            view.goBack()
        }

    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun refresh() {
        view.showKeyboard()
        refreshConfirmButton()
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