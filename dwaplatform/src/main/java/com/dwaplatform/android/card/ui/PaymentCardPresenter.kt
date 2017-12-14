package com.dwaplatform.android.card.ui

import com.dwaplatform.android.card.CardAPI
import javax.inject.Inject

/**
 * Created by ingrid on 13/12/17.
 */
class PaymentCardPresenter @Inject constructor(var view: PaymentCardContract.View,
                                               var api: CardAPI): PaymentCardContract.Presenter {

    override fun refreshConfirmButton() {
        val isEnabled = view.getNumberTextLength() >= 16
                && view.getDateTextLength() >= 4
                && view.getCCvTextLength() >= 3
        view.confirmButtonEnable(isEnabled)
    }

    override fun onConfirm() {
        view.confirmButtonEnable(false)

        view.showCommunicationWait()

        //TODO see CardApi
//        api.createCreditCard(
//                dbUsersHelper.userid(),
//                view.getNumberText(),
//                view.getDAteText(),
//                view.getCCvText()) { optusercreditcard, opterror ->
//
//            view.hideCommunicationWait()
//
//            refreshConfirmButton()
//
//            if (opterror != null) {
//                view.showCommunicationInternalError()
//                return@createCreditCard
//            }
//
//            if (optusercreditcard == null) {
//                view.showCommunicationInternalError()
//                return@createCreditCard
//            }
//            dbccardhelper.replace(optusercreditcard)
//            view.goBack()
//        }
//

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