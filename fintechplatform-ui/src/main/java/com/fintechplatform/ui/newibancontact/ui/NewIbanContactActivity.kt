package com.fintechplatform.ui.newibancontact.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.activity_newibancontact.*
import javax.inject.Inject

class NewIbanContactActivity : AppCompatActivity(), NewIbanContactContract.View {

    @Inject lateinit var presenter: NewIbanContactContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newibancontact)

        nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        surnameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        ibanText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        bicText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        forwardButton.isEnabled = isEnable
    }

    override fun getNameText(): String {
        return nameText.text.toString()
    }

    override fun getSurnameText(): String {
        return surnameText.text.toString()
    }

    override fun getBicText(): String {
        return bicText.text.toString()
    }

    override fun getIbanText(): String {
        return ibanText.text.toString()
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.navheader_abort)
    }

    override fun setBackwardText() {
        backwardButton.text = resources.getString(R.string.navheader_back)
    }
}
