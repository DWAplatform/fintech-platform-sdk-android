package com.fintechplatform.android.models

/**
 * Created by tcappellari on 08/12/2017.
 */
open class DataAccount(val ownerId: String,
                       val accountId: String,
                       val accountType: String,
                       val tenantId: String,
                       val accessToken: String,
                       val primaryAccountId: String?=null)