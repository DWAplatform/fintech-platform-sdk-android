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
                && !token.isNullOrEmpty()

        view.confirmButtonEnable(isEnabled)
    }

    override fun initPaymentCard(){
        paymentCardpersistanceDB.deletePaymentCard()
        api.getPaymentCards(token!!, dataAccountHelper.userId){ optcards, opterror ->

            if (opterror != null) {
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
                token!!,
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
                if (retries > 2)
                    view.showCommunicationInternalError()
                else {
                    retries++
                    dataAccountHelper.accountToken(true) { opttoken ->

                        token = opttoken
                        onConfirm()
                    }
                }
            else ->
                view.showCommunicationInternalError()
        }
    }
}