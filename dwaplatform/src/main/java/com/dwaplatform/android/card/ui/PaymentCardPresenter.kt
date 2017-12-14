package com.dwaplatform.android.card.ui

import com.dwaplatform.android.card.CardAPI
import com.dwaplatform.android.card.db.PaymentCardDB
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import javax.inject.Inject

/**
 * Created by ingrid on 13/12/17.
 */
class PaymentCardPresenter @Inject constructor(var view: PaymentCardContract.View,
                                               var api: CardAPI,
                                               val paymentCardpersistanceDB: PaymentCardPersistenceDB): PaymentCardContract.Presenter {

    override fun refreshConfirmButton() {
        val isEnabled = view.getNumberTextLength() >= 16
                && view.getDateTextLength() >= 4
                && view.getCCvTextLength() >= 3
        view.confirmButtonEnable(isEnabled)
    }

    override fun onConfirm() {
        view.confirmButtonEnable(false)

        view.showCommunicationWait()

        api.registerCard("token",
                view.getNumberText(),
                view.getDAteText(),
                view.getCCvText()) { optcard, opterror ->

            view.hideCommunicationWait()

            refreshConfirmButton()

            if (opterror != null) {
                view.showCommunicationInternalError()
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
}