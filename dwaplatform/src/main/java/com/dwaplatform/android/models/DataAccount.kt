package com.dwaplatform.android.models

import android.support.v7.app.AppCompatActivity

/**
 * Created by tcappellari on 08/12/2017.
 */
open class DataAccount(val userId: String,
                       val accountId: String,
                       val accountToken: (Boolean, (String) -> Unit) -> Unit)





