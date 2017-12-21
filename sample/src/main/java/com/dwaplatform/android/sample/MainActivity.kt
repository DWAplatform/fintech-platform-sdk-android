package com.dwaplatform.android.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dwaplatform.android.DWAplatform
import com.dwaplatform.android.payin.models.PayInConfiguration
import com.dwaplatform.android.sample.db.Animals
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.SQLite
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //FlowManager.init(this)




        val hostName = "https://api.sandbox.dwaplatform.com"
        DWAplatform.initialize(this)

        /*val pet = Animals()
        pet.id = "123456\n"
        pet.kind = "cat\n"
        pet.name = "gatto\n"
        pet.save()*/

        button.setOnClickListener {
            val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
            val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"
            val paymentCardId = null//"7e30a4d8-918b-11e7-931d-8b5fb14500e0"

            val builder = DWAplatform.buildPayIn()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,true, PayInConfiguration(userId, accountId, paymentCardId))

            payInComponent.payInUI.start(this@MainActivity)

            /*val query = SQLite.select().from(Animals::class.java).querySingle()
            query?.let {
                Log.d("HALLELUJA", it.id + it.kind + it.name)
            }*/
        }
    }
}
