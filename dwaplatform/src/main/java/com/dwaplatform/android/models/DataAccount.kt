package com.dwaplatform.android.models

/**
 * Created by tcappellari on 08/12/2017.
 */
open class DataAccount(val userId: String,
                       val accountId: String,
                       val accountToken: ((String) -> Unit) -> Unit)





