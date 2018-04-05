package com.fintechplatform.ui.newibancontact.ui

interface NewIbanContactContract {
    interface View {
        fun getNameText(): String
        fun getSurnameText(): String
        fun getBicText(): String
        fun getIbanText(): String
        fun setAbortText()
        fun setBackwardText()
        fun enableConfirmButton(isEnable: Boolean)
    }

    interface Presenter {
        fun refreshConfirmButton()
    }
}