package com.dwaplatform.android.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dwaplatform.android.DWAplatform
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.sample.auth.AuthBuilder
import com.dwaplatform.android.sample.auth.keys.KeyChain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//        val hostName = "http://192.168.1.73:9000"
//        val userId = "22863c52-ca29-11e7-94c6-b35ff6d69059"
//        val accountId = "22b50b86-ca29-11e7-90f2-dbaf50363c38"
    val hostName = "https://api.sandbox.dwaplatform.com"
    val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
    val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DWAplatform.initialize(this)

        payin.setOnClickListener {
            val builder = DWAplatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(userId, accountId, ::requestToken ))

            payInComponent.payInUI.start(this@MainActivity)

        }

        payout.setOnClickListener {
            val builder = DWAplatform.buildPayOut().createPayOutUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.payOutUI.start(this)
        }

        transactions.setOnClickListener {
            val builder = DWAplatform.buildTransactions().createTransactionsUI(hostName, DataAccount(userId,accountId,::requestToken))
            builder.transactiosUI.start(this)
        }

        ligtdata.setOnClickListener {
            val builder = DWAplatform.buildProfile().createLightDataUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.lightData.start(this)
        }

        contacts.setOnClickListener {
            val builder = DWAplatform.buildProfile().createContactsUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.contactsUI.start(this)
        }

        address.setOnClickListener {
            val builder = DWAplatform.buildProfile().createAddressUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.addressUI.start(this)
        }

        job.setOnClickListener {
            val builder = DWAplatform.buildProfile().createJobInfoUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.jobInfoUI.start(this)
        }

        idcards.setOnClickListener {
            val builder = DWAplatform.buildProfile().createIdCardsUI(hostName, DataAccount(userId, accountId,::requestToken))
            builder.identityCardsUI.start(this)
        }

        card.setOnClickListener {
            val builder = DWAplatform.buildPaymentCardBuilder().createPaymentCardUI(hostName, true, DataAccount(userId, accountId,::requestToken))
            builder.paymentCardUI.start(this)
        }

        iban.setOnClickListener {
            val builder = DWAplatform.buildIBAN().createIBANUIComponent(hostName, DataAccount(userId,accountId,::requestToken))
            builder.ibanUI.start(this)
        }
    }

    fun requestToken(completion: (String) -> Unit) {
        val token = KeyChain(this)["accountToken"]
        if (token.isNotEmpty())
            completion(token)
        else
        // nota: se chiamate multiple, prima che activity ritorni, verrà chiamata la callback relativa all'ultima chiamata.
            AuthBuilder()
                    .createAuthUI(userId, hostName, completion)
                    .authUI.start(this@MainActivity)
    }

}
