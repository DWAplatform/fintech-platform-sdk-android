package com.fintechplatform.android.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintechplatform.android.DWAplatform
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.sample.auth.AuthBuilder
import com.fintechplatform.android.sample.auth.keys.KeyChain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


//    val hostName = "http://10.0.0.7:9000"
//    val userId = "22863c52-ca29-11e7-94c6-b35ff6d69059"
//    val accountId = "22b50b86-ca29-11e7-90f2-dbaf50363c38"
    val hostName = "https://api.sandbox.dwaplatform.com"
    val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
    val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"

    var token = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DWAplatform.initialize(this)


        payin.setOnClickListener {
            val builder = DWAplatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(userId, accountId, token))

            payInComponent.payInUI.start(this@MainActivity)
        }

        clean.setOnClickListener {
            KeyChain(this)["accessToken"] = ""
        }

        auth.setOnClickListener {
            AuthBuilder().createAuthUI(userId, hostName).authUI.start(this)
        }

        payout.setOnClickListener {
            val builder = DWAplatform.buildPayOut().createPayOutUI(hostName, DataAccount(userId, accountId, token))
            builder.payOutUI.start(this)
        }

        transactions.setOnClickListener {
            val builder = DWAplatform.buildTransactions().createTransactionsUI(hostName, DataAccount(userId,accountId, token))
            builder.transactiosUI.start(this)
        }

        ligtdata.setOnClickListener {
            val builder = DWAplatform.buildProfile().createLightDataUI(hostName, DataAccount(userId, accountId, token))
            builder.lightData.start(this)
        }

        contacts.setOnClickListener {
            val builder = DWAplatform.buildProfile().createContactsUI(hostName, DataAccount(userId, accountId, token))
            builder.contactsUI.start(this)
        }

        address.setOnClickListener {
            val builder = DWAplatform.buildProfile().createAddressUI(hostName, DataAccount(userId, accountId, token))
            builder.addressUI.start(this)
        }

        job.setOnClickListener {
            val builder = DWAplatform.buildProfile().createJobInfoUI(hostName, DataAccount(userId, accountId, token))
            builder.jobInfoUI.start(this)
        }

        idcards.setOnClickListener {
            val builder = DWAplatform.buildProfile().createIdCardsUI(hostName, DataAccount(userId, accountId, token))
            builder.identityCardsUI.start(this)
        }

        card.setOnClickListener {
            val builder = DWAplatform.buildPaymentCardBuilder().createPaymentCardUI(hostName, true, DataAccount(userId, accountId, token))
            builder.paymentCardUI.start(this)
        }

        iban.setOnClickListener {
            val builder = DWAplatform.buildIBAN().createIBANUIComponent(hostName, DataAccount(userId,accountId, token))
            builder.ibanUI.start(this)
        }

        financial.setOnClickListener {
            val builder = DWAplatform.buildFinancialData().createFinancialsUI(hostName, DataAccount(userId, accountId, token), true)
            builder.financialDataUI.start(this)
        }

        bankfinancial.setOnClickListener {
            val builder = DWAplatform.buildFinancialData().createFinancialBankInfo(hostName, DataAccount(userId, accountId, token), true)
            builder.financialDataUI.start(this)
        }

        enterpriseinfo.setOnClickListener {
            val builder = DWAplatform.buildEnterpriseData().buildEnterpriseInfoUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseInfoUI.start(this)
        }

        enterprisecontacts.setOnClickListener {
            val builder = DWAplatform.buildEnterpriseData().buildEnterpriseContactsUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseContactsUI.start(this)
        }

        enterpriseaddress.setOnClickListener {
            val builder = DWAplatform.buildEnterpriseData().buildEnterpriseAddressUIComponent(hostName, DataAccount(userId, accountId, token))
            builder.enterpriseAddressUI.start(this)
        }

        enterprisedocs.setOnClickListener {
            val builder = DWAplatform.buildEnterpriseData().buildEnterpriseDocumentsUIComponent(hostName, DataAccount(userId, accountId, token))
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
