Fintechplatform Android SDK
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


Sample usage in Kotlin
-------------------------------------------------
```kotlin

    // Simply initialize Fintech Platform giving Context params
    
    
    FintechPlatform.initialize(this)
    
    

    // Get UI component you want.
    // Components needs to have your own configuration as params (hostname, userid, accountid and token access to the platform)
    
                val dataAccount = DataAccount(userId, accountId, accessToken)
                
                FintechPlatform.buildPayIn()
                        .createPayInUIComponent(hostName, true, dataAccount)
                        .cashInUI.start(this)
    
                FintechPlatform.buildPayOut()
                        .createPayOutUI(hostName, dataAccount)
                        .payOutUI.start(this)
                        
    // If you don't need any UI components and you want to use your own graphics we also provide API Components instances, in this example  
    
                val api = FintechPlatform.buildPayIn()
                        .createPayInAPIComponent(Configurations.hostName, this)
                        .payInAPI
                        
```
Building and Running the sample app project
-------------------------------------------------

You can run the [Sample Fintech SDK](https://github.com/nabertech/fintech-android-sdk) application.

You don't need to register you personal data, we've already provided a test account using sandbox server APIs, in order to give you the opportunity to see some of the platform features:

1. make test payments through the app using a fake payment card
2. register a new payment card
3. register a bank account through IBAN address
4. cashout from the Fintech mobile account to your bank account using IBAN you gave during the registration
5. see all the transactions