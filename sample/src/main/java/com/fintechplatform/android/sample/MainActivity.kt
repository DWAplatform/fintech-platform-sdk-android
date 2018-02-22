package com.fintechplatform.android.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.android.FintechPlatform
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.sample.auth.AuthBuilder
import com.fintechplatform.android.sample.auth.keys.KeyChain
import com.fintechplatform.android.sample.contactslist.NetworkListBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val hostName = "http://192.168.1.66:9000"
        val userId = "69ecae2e-3c7d-403e-a4a2-0ef834bc9b76"
        val accountId = "ac11db07-a7b7-494a-ad6e-0b86a373cc4b"
        val tenantId = "f7569f0e-aaa7-11e7-b71f-ff13485d8836"
        val accountType = "BUSINESS"
    }

    var token = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FintechPlatform.initialize(this)


        payin.setOnClickListener {
            val builder = FintechPlatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(userId, accountId, accountType, tenantId, token))

            payInComponent.payInUI.start(this@MainActivity)
        }

        clean.setOnClickListener {
            KeyChain(this)["accessToken"] = ""
            this.onResume()
        }

        auth.setOnClickListener {
            AuthBuilder().createAuthUI(userId, hostName, tenantId).authUI.start(this)
        }

        payout.setOnClickListener {
            val builder = FintechPlatform.buildPayOut().createPayOutUI(hostName, DataAccount(userId, accountId, accountType, tenantId, token))
            builder.payOutUI.start(this)
        }

        transactions.setOnClickListener {
            val builder = FintechPlatform.buildTransactions().createTransactionsUI(hostName, DataAccount(userId,accountId,accountType, tenantId, token))
            builder.transactiosUI.start(this)
        }

        ligtdata.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createLightDataUI(hostName, DataAccount(userId, accountId,accountType, tenantId, token))
            builder.lightData.start(this)
        }

        contacts.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createContactsUI(hostName, DataAccount(userId, accountId,accountType,  tenantId,token))
            builder.contactsUI.start(this)
        }

        address.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createAddressUI(hostName, DataAccount(userId, accountId,accountType,  tenantId, token))
            builder.addressUI.start(this)
        }

        job.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createJobInfoUI(hostName, DataAccount(userId, accountId,accountType,  tenantId, token))
            builder.jobInfoUI.start(this)
        }

        idcards.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createIdCardsUI(hostName, DataAccount(userId, accountId,accountType, tenantId, token))
            builder.identityCardsUI.start(this)
        }

        card.setOnClickListener {
            val builder = FintechPlatform.buildPaymentCardBuilder().createPaymentCardUI(hostName, true, DataAccount(userId, accountId,accountType,  tenantId, token))
            builder.paymentCardUI.start(this)
        }

        iban.setOnClickListener {
            val builder = FintechPlatform.buildIBAN().createIBANUIComponent(hostName, DataAccount(userId,accountId, accountType, tenantId, token))
            builder.ibanUI.start(this)
        }

        financial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createFinancialsUI(hostName, DataAccount(userId, accountId,accountType,  tenantId, token), true)
            builder.financialDataUI.start(this)
        }

        bankfinancial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createFinancialBankInfo(hostName, DataAccount(userId, accountId,accountType,  tenantId, token), true)
            builder.financialDataUI.start(this)
        }

        enterpriseinfo.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseInfoUIComponent(hostName, DataAccount(userId, accountId, accountType, tenantId, token))
            builder.enterpriseInfoUI.start(this)
        }

        enterprisecontacts.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseContactsUIComponent(hostName, DataAccount(userId, accountId, accountType, tenantId, token))
            builder.enterpriseContactsUI.start(this)
        }

        enterpriseaddress.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseAddressUIComponent(hostName, DataAccount(userId, accountId, accountType, tenantId, token))
            builder.enterpriseAddressUI.start(this)
        }

        enterprisedocs.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseDocumentsUIComponent(hostName, DataAccount(userId, accountId,accountType,  tenantId, token))
            builder.enterpriseDocumentsUI.start(this)
        }

        peers.setOnClickListener {
            NetworkListBuilder().buildNetworkComponent(hostName, DataAccount(userId, accountId, accountType, tenantId, token))
                    .networkListUI.start(this)
        }
    }

    override fun onResume() {
        super.onResume()

        token = KeyChain(this)["accessToken"]

        if(token.isEmpty()) {
            AuthBuilder().createAuthUI(userId, hostName, tenantId).authUI.start(this)
        }

    }
}
