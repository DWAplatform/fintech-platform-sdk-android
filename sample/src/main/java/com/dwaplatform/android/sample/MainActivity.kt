package com.dwaplatform.android.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dwaplatform.android.DWAplatform
import com.dwaplatform.android.payin.models.PayInConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val config = DWAplatform.Configuration("10.0.0.4:9000", true)
        DWAplatform.initialize(config, this)


        // Get card API
        val cardAPI = DWAplatform.getCardAPI(this)

        // Register card
        // get token from POST call: .../rest/v1/:clientId/users/:userId/accounts/:accountId/cards
        //final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MDk5ODMyNzIsImlhdCI6MTUwOTk3OTY3MiwiY2xpZW50SWQiOiJmNzU2OWYwZS1hYWE3LTExZTctYjcxZi1mZjEzNDg1ZDg4MzYiLCJ1c2VySWQiOiI2MzlhZmM5OC1jMDhiLTExZTctYjUwNC1mZmMxOGIxNDc4NDAiLCJhY2NvdW50SWQiOiJhMDNkMDcyNi1jMDhjLTExZTctYjUwNC00YjYzZjNiNzFjZTEiLCJjYXJkSWQiOiI3N2I5ZTQ5Ni1jMzAxLTExZTctYThlOS03N2M3MWFhNGI4ZjYifQ.wX8xlFzbi1LFwrIAOn3H62R2D_SrOOcychqiv6ndccZk_0kWmp5uJDGqr7QT3qJ4conEze-wYb05m0qjv0pTDw";
        //final String cardNumber = "1234567812345678";
        //final String expiration = "1122";
        //final String cxv = "123";
        /*
        cardAPI.registerCard(token, cardNumber, expiration, cxv, new Function2<PaymentCard, Exception, Unit>() {
            @Override
            public Unit invoke(PaymentCard card, Exception e) {
                // now you can access to card object to request cashin, etc.
                if(e != null) {
                    Log.e("Sample", e.getMessage());
                } else {
                    Log.d("Sample", card.getId());
                }
                return Unit.INSTANCE;
            }
        });*/

        val hostName = "https://api.sandbox.dwaplatform.com"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1MTMyNTkzODUsImlhdCI6MTUxMzE3Mjk4NSwidXNlcmlkIjoiNjBlMjc5ZWMtOTE4Yi0xMWU3LThkNTUtZGJhYWEyNGNjNWMyIn0.Xy2P9DpFU3ApprvNM5mGnuqDc9WDbJE5keDrvTy5IDe8zMXnqzV_asOpPljOelmlqqKJaVYygMJEActCPCdn-Q"

        button.setOnClickListener {
            val userId = "60e279ec-918b-11e7-8d55-dbaaa24cc5c2"
            val accountId = "722c4d74-ba56-11e7-8487-a7b42609a5c1"
            val paymentCardId = "7e30a4d8-918b-11e7-931d-8b5fb14500e0"


            val builder = DWAplatform.buildPayIn()
            val builder3d = DWAplatform.build3DSecure()
            val payInComponent = builder.createPayInUIComponent(
                    hostName,
                    token, PayInConfiguration(userId, accountId, paymentCardId))

            payInComponent.payInUI.start(this@MainActivity)
        }
    }
}
