Fintech Platform Android SDK
=================================================
Fintech platform is an Android client libraryto work with Fintech Platform.

Installation
-------------------------------------------------

### Android Studio (or Gradle)

No need to clone the repository or download any files -- just add this line to your app's `build.gradle` inside the `dependencies` section:

    implementation 'com.fintechplatform:fintechplatform-api:1.0.0'
    


License
-------------------------------------------------
Fintech Platform SDK is distributed under MIT license, see LICENSE file.


Contacts
-------------------------------------------------
Report bugs or suggest features using
[issue tracker at GitHub](https://github.com/DWAplatform/fintech-platform-sdk-android/issues).

Features
-------------------------------------------------
We supply the following modules:

###### fintechplatform-api

1. cash in 
2. cash out
3. balance
4. payment card registration
5. bank account, IBAN address registration
6. transactions list
7. 3Dsecure
8. (profile) personal informations
9. (enterprise profile) company informations

###### fintechplatform-ui
1. cash in 
2. cash out
3. balance
4. payment card registration
5. bank account, IBAN address registration
6. transactions list
7. 3Dsecure
8. (profile) personal informations
9. (enterprise profile) company informations


Sample usage CashIn API Component in Kotlin
-------------------------------------------------
######Fintech Account (accountId) is credited with 20,00 € using a card (cardId) owned by the user (userId)


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
    val cashInAPI = FintechPlatform.buildPayIn()
            .createCashInAPIComponent(hostName, context)
            .cashInAPI
     
    // Start Payin
    cashInAPI.cashIn(accessToken, 
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
Sample usage CashIn UI Component in Kotlin
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
    
    val cashInUI = FintechPlatform.buildCashIn()
                    .createCashInUIComponent(hostName, isSandbox, dataAccount)
                    .cashInUI
                    
    cashInUI.start(context)
    
```
