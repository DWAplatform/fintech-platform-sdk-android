package com.fintechplatform.api.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.api.sample.auth.AuthBuilder
import com.fintechplatform.api.sample.auth.keys.KeyChain
import com.fintechplatform.api.sample.contactslist.NetworkListBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val hostName = "http://192.168.1.73:9000"
        val ownerId = "9439da74-8a02-4234-8c1c-f9f39f41c7eb"
        val accountId = "ea55c17c-37f1-473b-be38-9f1e73071ff8"
        val tenantId = "f7569f0e-aaa7-11e7-b71f-ff13485d8836"
        val userId = "9439da74-8a02-4234-8c1c-f9f39f41c7eb"
        val accountType = "PERSONAL"
    }

    var token = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FintechPlatform.initialize(this)


        payin.setOnClickListener {
            val builder = FintechPlatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(ownerId, accountId, accountType, tenantId, token))

            payInComponent.payInUI.start(this@MainActivity)
        }

        clean.setOnClickListener {
            KeyChain(this)["accessToken"] = ""
            this.onResume()
        }

        auth.setOnClickListener {
            AuthBuilder().createAuthUI(ownerId, hostName, tenantId).authUI.start(this)
        }

        payout.setOnClickListener {
            val builder = FintechPlatform.buildPayOut().createPayOutUI(hostName, DataAccount(ownerId, accountId, accountType, tenantId, token))
            builder.payOutUI.start(this)
        }

        transactions.setOnClickListener {
            val builder = FintechPlatform.buildTransactions().createTransactionsUI(hostName, DataAccount(ownerId,accountId,accountType, tenantId, token))
            builder.transactiosUI.start(this)
        }

        ligtdata.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createLightDataUI(hostName, DataAccount(ownerId, accountId,accountType, tenantId, token))
            builder.lightData.start(this)
        }

        contacts.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createContactsUI(hostName, DataAccount(ownerId, accountId,accountType,  tenantId,token))
            builder.contactsUI.start(this)
        }

        address.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createAddressUI(hostName, DataAccount(ownerId, accountId,accountType,  tenantId, token))
            builder.addressUI.start(this)
        }

        job.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createJobInfoUI(hostName, DataAccount(ownerId, accountId,accountType,  tenantId, token))
            builder.jobInfoUI.start(this)
        }

        idcards.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createIdCardsUI(hostName, DataAccount(ownerId, accountId,accountType, tenantId, token))
            builder.identityCardsUI.start(this)
        }

        card.setOnClickListener {
            val builder = FintechPlatform.buildPaymentCardBuilder().createPaymentCardUI(hostName, true, DataAccount(ownerId, accountId,accountType,  tenantId, token))
            builder.paymentCardUI.start(this)
        }

        iban.setOnClickListener {
            val builder = FintechPlatform.buildIBAN().createIBANUIComponent(hostName, DataAccount(ownerId,accountId, accountType, tenantId, token))
            builder.ibanUI.start(this)
        }

        financial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createMangoPayFinancialDataUI(hostName, DataAccount(ownerId, accountId,accountType,  tenantId, token), true)
            builder.payInPayOutFinancialDataUI.start(this)
        }

        bankfinancial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createSellaFinancialDataUI(hostName, DataAccount(ownerId, accountId,accountType,  tenantId, token), true)
            builder.financialDataUI.start(this)
        }

        enterpriseinfo.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseInfoUIComponent(hostName, DataAccount(ownerId, accountId, accountType, tenantId, token))
            builder.enterpriseInfoUI.start(this)
        }

        enterprisecontacts.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseContactsUIComponent(hostName, DataAccount(ownerId, accountId, accountType, tenantId, token))
            builder.enterpriseContactsUI.start(this)
        }

        enterpriseaddress.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseAddressUIComponent(hostName, DataAccount(ownerId, accountId, accountType, tenantId, token))
            builder.enterpriseAddressUI.start(this)
        }

        enterprisedocs.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseDocumentsUIComponent(hostName, DataAccount(ownerId, accountId,accountType,  tenantId, token))
            builder.enterpriseDocumentsUI.start(this)
        }

        peers.setOnClickListener {
            NetworkListBuilder().buildNetworkComponent(hostName, DataAccount(ownerId, accountId, accountType, tenantId, token))
                    .networkListUI.start(this)
        }

        sct.setOnClickListener {
            return@setOnClickListener
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
