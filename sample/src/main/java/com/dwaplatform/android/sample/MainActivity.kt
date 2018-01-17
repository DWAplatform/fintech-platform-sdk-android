package com.dwaplatform.android.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dwaplatform.android.DWAplatform
import com.dwaplatform.android.account.balance.db.Balance
import com.dwaplatform.android.card.db.PaymentCard
import com.dwaplatform.android.card.db.PaymentCardDB
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.card.models.PaymentCardItem
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.profile.db.user.Users
import com.dwaplatform.android.sample.auth.AuthBuilder
import com.dwaplatform.android.sample.auth.keys.KeyChain
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.sql.language.SQLite
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DWAplatform.initialize(this)

        val hostName = "http://192.168.1.73:9000"
        val userId = "22863c52-ca29-11e7-94c6-b35ff6d69059"
        val accountId = "22b50b86-ca29-11e7-90f2-dbaf50363c38"
       // val hostName = "https://api.sandbox.dwaplatform.com"
//        val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
//        val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"

        /*val pet = Animals()
        pet.id = "123456\n"
        pet.kind = "cat\n"
        pet.name = "gatto\n"
        pet.save()*/
        val token = KeyChain(this)["accessToken"]

        if(token.isNullOrEmpty()) {
            AuthBuilder().createAuthUI(userId, hostName).authUI.start(this)
        }

        payin.setOnClickListener {
            val builder = DWAplatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, DataAccount(userId, accountId, token))

            payInComponent.payInUI.start(this@MainActivity)
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
    }
}
