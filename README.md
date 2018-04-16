[ ![Download](https://api.bintray.com/packages/dwafintech/fintechplatform/sdk-android/images/download.svg) ](https://bintray.com/dwafintech/fintechplatform/sdk-android/_latestVersion)

Fintech Platform Android SDK
=================================================
Fintech platform is an Android client library to work with Fintech Platform.

Installation
-------------------------------------------------

### Android Studio (or Gradle)

No need to clone the repository or download any files -- just add this line to your app's `build.gradle` inside the `dependencies` section:

For API module only

    implementation 'com.fintech.platform:fintechplatform-api:1.2.3'
    
For UI module

    implementation 'com.fintech.platform:fintechplatform-api:1.2.3'
    implementation 'com.fintech.platform:fintechplatform-ui:1.2.2'
    
Make sure you have jcenter() repository added in your build.gradle file at the Project level

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

1. Cash in 
2. Cash out
3. Balance
4. Payment card registration
5. IBAN address registration
6. Transactions list
7. Peer to peer transfers
8. (profile) User personal informations
9. (enterprise profile) Company informations

###### fintechplatform-ui
1. Cash in / 3DSecure
2. Cash out
4. Linking payment card to Fintech Platform Account
5. Linking bank account, with IBAN address and User Residential data (because needed) to Fintech Platform Account
6. Transactions list
7. Peer to peer transfers
8. Personal informations: 
    * general informations
    * contacts: email and telephone number
    * residential address
    * job information
    * IDs documents
9. Company informations
    * general informations
    * contacts
    * quarter general address
    * documents


Sample usage CashIn API Component in Kotlin
-------------------------------------------------

Fintech Account (accountId) is credited with 20,00 € using a card (cardId) owned by the user (userId)


```kotlin

    import com.fintechplatform.api.FintechPlatformAPI
    import com.fintechplatform.api.money.Money
    
    // ....

    //  Server host parameters
    val hostName = "FINTECH_PLATFORM_[SANDBOX]_URL"
    val accessToken = "XXXXXXYYYYYY.....ZZZZZZ"
        

    //  Set User Account Linked Card parameters
    val tenantId = "87e4ff86-18b6-44cf-87af-af2411ab68c5"
    val userId = "08ad02e8-89fb-44b8-ab65-87eea175adc2"
    val accountId = "f0c84dbc-5d1d-4973-b212-1ac2cd34e5c3"
    val cardId = "2bde23fc-df93-4ff2-acce-51f42be62062"
        

    //  Amount to cashIn
    val amountToCashIn = Money(2000) // amount in euro cent

    //  Optional Idempotency
    val idempotencyKey = "idemp1"

    //  create cash in API interface
    val cashInAPI = FintechPlatformAPI.getPayIn(hostName, context)
                

    //  Start Cash in
    cashInAPI.cashIn(accessToken,
                    userId,
                    accountId,
                    "PERSONAL",
                    tenantId,
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
           // 3d secure required
           goToSecure3D(payinreply.redirecturl)
        } else {
           // Cash in completed
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
