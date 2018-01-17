package com.dwaplatform.android.card.ui

import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.card.api.PaymentCardAPI
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.models.DataAccount
import javax.inject.Inject

/**
 * Created by ingrid on 13/12/17.
 */
class PaymentCardPresenter @Inject constructor(var view: PaymentCardContract.View,
                                               var api: PaymentCardAPI,
                                               var dataAccountHelper: DataAccount,
                                               val paymentCardpersistanceDB: PaymentCardPersistenceDB ): PaymentCardContract.Presenter {

    override fun refreshConfirmButton() {

        val isEnabled = view.getNumberTextLength() >= 16
                && view.getDateTextLength() >= 4
                && view.getCCvTextLength() >= 3
                && !dataAccountHelper.accessToken.isNullOrEmpty()

        view.confirmButtonEnable(isEnabled)
    }

    override fun initPaymentCard(){
        paymentCardpersistanceDB.deletePaymentCard()
        api.getPaymentCards(dataAccountHelper.accessToken, dataAccountHelper.userId){ optcards, opterror ->

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

        api.createCreditCard(dataAccountHelper.userId,
                dataAccountHelper.accountId,
                dataAccountHelper.accessToken,
                view.getNumberText(),
                view.getDAteText(),
                view.getCCvText()) { optcard, opterror ->

            view.hideCommunicationWait()

            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@createCreditCard
            }

            if (optcard == null) {
                view.showCommunicationInternalError()
                return@createCreditCard
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