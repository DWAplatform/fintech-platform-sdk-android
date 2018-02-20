package com.fintechplatform.android.enterprise.info.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.fintechplatform.android.R
import com.fintechplatform.android.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_infoenterprise.*
import javax.inject.Inject

/**
 * Shows enterprise informations
 */
class EnterpriseInfoActivity : AppCompatActivity(), EnterpriseInfoContract.View {

    @Inject lateinit var presenter: EnterpriseInfoContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EnterpriseInfoUI.instance.createLightDataViewComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_infoenterprise)

        presenter.initialize()

        nameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        enterpriseTypeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        enterpriseTypeLayout.setOnClickListener {

        }

        abortButton.setOnClickListener { presenter.onAbortClick() }

    }

    override fun onBackPressed() {
        presenter.onAbortClick()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setBackwardText() {
        abortButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        abortButton.text = resources.getString(R.string.abort)
    }

    override fun enableAllTexts(areEnables: Boolean) {
        nameText.isEnabled = areEnables
        enterpriseTypeText.isEnabled = areEnables
    }

    override fun getNameText(): String {
        return nameText.text.toString()
    }

    override fun getEnterpriseType(): String {
        return enterpriseTypeText.text.toString()
    }

    override fun setNameText(name: String){
        nameText.setText(name)
    }

    override fun setEnterpriseType(surname: String) {
        enterpriseTypeText.setText(surname)
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showTokenExpired() {
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
}