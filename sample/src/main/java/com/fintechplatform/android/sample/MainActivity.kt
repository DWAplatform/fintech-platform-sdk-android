package com.fintechplatform.android.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.android.FintechPlatform
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.sample.auth.AuthBuilder
import com.fintechplatform.android.sample.auth.keys.KeyChain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val hostName = "https://api.sandbox.dwaplatform.com"
    val userId = "6d53abf6-01b6-11e8-bf67-1beac29e3d21"
    val accountId = "6e2d548c-01b6-11e8-bf68-47f8630c0ceb"

    var token = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FintechPlatform.initialize(this)


        payin.setOnClickListener {
            val builder = FintechPlatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(userId, accountId, token))

            payInComponent.payInUI.start(this@MainActivity)
        }

        clean.setOnClickListener {
            KeyChain(this)["accessToken"] = ""
            this.onResume()
        }

        auth.setOnClickListener {
            AuthBuilder().createAuthUI(userId, hostName).authUI.start(this)
        }

        payout.setOnClickListener {
            val builder = FintechPlatform.buildPayOut().createPayOutUI(hostName, DataAccount(userId, accountId, token))
            builder.payOutUI.start(this)
        }

        transactions.setOnClickListener {
            val builder = FintechPlatform.buildTransactions().createTransactionsUI(hostName, DataAccount(userId,accountId, token))
            builder.transactiosUI.start(this)
        }

        ligtdata.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createLightDataUI(hostName, DataAccount(userId, accountId, token))
            builder.lightData.start(this)
        }

        contacts.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createContactsUI(hostName, DataAccount(userId, accountId, token))
            builder.contactsUI.start(this)
        }

        address.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createAddressUI(hostName, DataAccount(userId, accountId, token))
            builder.addressUI.start(this)
        }

        job.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createJobInfoUI(hostName, DataAccount(userId, accountId, token))
            builder.jobInfoUI.start(this)
        }

        idcards.setOnClickListener {
            val builder = FintechPlatform.buildProfile().createIdCardsUI(hostName, DataAccount(userId, accountId, token))
            builder.identityCardsUI.start(this)
        }

        card.setOnClickListener {
            val builder = FintechPlatform.buildPaymentCardBuilder().createPaymentCardUI(hostName, true, DataAccount(userId, accountId, token))
            builder.paymentCardUI.start(this)
        }

        iban.setOnClickListener {
            val builder = FintechPlatform.buildIBAN().createIBANUIComponent(hostName, DataAccount(userId,accountId, token))
            builder.ibanUI.start(this)
        }

        financial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createFinancialsUI(hostName, DataAccount(userId, accountId, token), true)
            builder.financialDataUI.start(this)
        }

        bankfinancial.setOnClickListener {
            val builder = FintechPlatform.buildFinancialData().createFinancialBankInfo(hostName, DataAccount(userId, accountId, token), true)
            builder.financialDataUI.start(this)
        }

        enterpriseinfo.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseInfoUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseInfoUI.start(this)
        }

        enterprisecontacts.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseContactsUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseContactsUI.start(this)
        }

        enterpriseaddress.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseAddressUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseAddressUI.start(this)
        }

        enterprisedocs.setOnClickListener {
            val builder = FintechPlatform.buildEnterpriseData().buildEnterpriseDocumentsUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseDocumentsUI.start(this)
        }
    }

    override fun onResume() {
        super.onResume()

        token = KeyChain(this)["accessToken"]

        if(token.isEmpty()) {
            AuthBuilder().createAuthUI(userId, hostName).authUI.start(this)
        }

    }
}
