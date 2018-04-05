package com.fintechplatform.ui.newibancontact.ui


class NewIbanContactPresenter constructor(val view: NewIbanContactContract.View): NewIbanContactContract.Presenter {

    override fun refreshConfirmButton() {
        view.setAbortText()
        val isEnabled = view.getIbanText().length >= 20 &&
                view.getNameText().isNotEmpty() &&
                view.getSurnameText().isNotEmpty() &&
                view.getBicText().isNotEmpty()

        view.enableConfirmButton(isEnabled)
    }
}