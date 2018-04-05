Fintech Platform Android SDK
=================================================
Fintech platform is an Android client library.

Installation
-------------------------------------------------

### Android Studio (or Gradle)

No need to clone the repository or download any files -- just add this line to your app's `build.gradle` inside the `dependencies` section:

    implementation 'com.fintechplatform:fintechplatform:1.1.0'
    


License
-------------------------------------------------
Fintech Platform SDK is distributed under MIT license, see LICENSE file.


Contacts
-------------------------------------------------
Report bugs or suggest features using
[issue tracker at GitHub](https://github.com/nabertech/fintech-android-sdk/issues).

Features
-------------------------------------------------
We supply the following modules:

1. pay in 
2. pay out
3. balance
4. payment card registration
5. bank account, IBAN address registration
6. transactions list
7. 3Dsecure
8. (profile) personal informations````
9. (enterprise profile) company informations

Each module provides a builder of a UI or API component.

Sample usage PayIn UI Component in Kotlin
-------------------------------------------------
```kotlin
    import com.fintechplatform.android.FintechPlatform
    import com.fintechplatform.android.models.DataAccount
    
    // Initialize Fintech Platform in your MainActivity or in your Application onCreate, and give it Context params
    val context = this as Context
    
    FintechPlatform.initialize(context)
    
    // Components needs to have your own configuration as params (hostname, userid, accountid and token access to the platform)
    // accessToken is the key that you received from server after PIN authentication process.
     
    val hostName = "FINTECH_PLATFORM_SANDBOX_URL"
    val userId = "asd34vfs-poc05098ncoij-aspdl"
    val accountId = "oijfvsoeij42309uvoije-oijbsf"
    val accessToken = "jmcjaspjdas023ucv9-2108-vjodawdBOIyhdfa0shPASo384-dcpaos-2edas"
    
    val isSandbox = true
      
    val dataAccount = DataAccount(userId, accountId, accessToken)
    
    val payInUI = FintechPlatform.buildPayIn()
                    .createPayInUIComponent(hostName, isSandbox, dataAccount)
                    .payInUI
                    
    payInUI.start(context)
    
```


Sample usage PayIn API Component in Kotlin
-------------------------------------------------

    // If you don't need any UI components and you want to use your own graphics we also provide API components instances, in this example you can see pay in use case: 
    NO: // Note: you need to supply your payment card ID, an idempotency UUID and how many cash you want to pay in, in this example 20,00 €

In  questo esempioi facciamo una ricarica di 20 euro attracerso la carta con id cardid sul conto accountd
dellutente userid


```kotlin
    import com.fintechplatform.android.FintechPlatform
    import com.fintechplatform.android.models.DataAccount
    import com.fintechplatform.android.money.Money
    
    // ....
    
    // Initialize Fintech Platform (e.g. add in your Application class)
    FintechPlatform.initialize(context)
   
    // Server host parameters
    val hostName = "FINTECH_PLATFORM_[SANDBOX]_URL"
    val accessToken = "XXXXXXYYYYYY.....ZZZZZZ"
    
    // Set User Account Linked Card parameters
    val userId = "08ad02e8-89fb-44b8-ab65-87eea175adc2"
    val accountId = "f0c84dbc-5d1d-4973-b212-1ac2cd34e5c3"
    val cardId = "2bde23fc-df93-4ff2-acce-51f42be62062"
    
    // Amount to cashIn
    val amountToCashIn = Money(2000, "EUR") // amount in euro cent
    
    // Optional Idempotency
    val idempotencyKey = "idemp1"
    
    // create payIn API interface
    val payInAPI = FintechPlatform.buildPayIn()
            .createPayInAPIComponent(hostName, context)
            .payInAPI
     
    // Start Payin
    payInAPI.payIn(accessToken, 
                    userId,
                    accountId,
                    cardId,
                    amountToCashIn,
                    idempotencyKey) { optpayinreply, opterror ->
             
                         if (opterror != null) {
                             handleErrors(opterror)
                             return@payIn
                         }
             
                         if (optpayinreply == null) {
                             handleNoReply()
                             return@payIn
                         }
                         
                         val payinreply = optpayinreply
                         if (payinreply.securecodeneeded) {
                             goToSecure3D(payinreply.redirecturl ?: "")
                         } else {
                             // pay in has just done
                         }
                    }
```

Example app
-------------------------------------------------

You can run the [Sample Fintech SDK application](https://github.com/nabertech/sample-fintech-android-sdk) to test some of the features.