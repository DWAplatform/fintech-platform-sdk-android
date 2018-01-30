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

Features
-------------------------------------------------
We supply these features as a single module to use:

1. pay in 
2. pay out
3. balance
4. payment card registration
5. bank account, IBAN address registration
6. transactions list
7. 3Dsecure
8. (profile) personal informations
9. (enterprise profile) company informations

Each module provides a builder of a UI or API component.

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
                        .payInUI.start(this)
    
                FintechPlatform.buildPayOut()
                        .createPayOutUI(hostName, dataAccount)
                        .payOutUI.start(this)
                        
    // If you don't need any UI components and you want to use your own graphics we also provide API components instances, in this example you can see cash in use case: 
    
                val api = FintechPlatform.buildPayIn()
                        .createPayInAPIComponent(Configurations.hostName, this)
                        .payInAPI
                            
    
```
Example app
-------------------------------------------------

You can run the [Sample Fintech SDK application](https://github.com/nabertech/fintech-android-sdk) to test some of the features.