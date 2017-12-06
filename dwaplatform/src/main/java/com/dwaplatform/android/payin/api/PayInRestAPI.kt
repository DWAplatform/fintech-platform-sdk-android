package com.dwaplatform.android.payin.api

import com.android.volley.DefaultRetryPolicy
import com.dwaplatform.android.api.IRequestProvider
import com.dwaplatform.android.api.IRequestQueue
import com.dwaplatform.android.card.helpers.JSONHelper
import com.dwaplatform.android.models.Amount
import java.util.HashMap

/**
 * Created by ingrid on 06/12/17.
 */
class PayInRestAPI constructor(
        internal val hostName: String,
        internal val token: String,
        internal val queue: IRequestQueue,
        internal val requestProvider: IRequestProvider,
        internal val jsonHelper: JSONHelper) {

    private val PROTOCOL_CHARSET = "utf-8"

    private fun getURL(path: String): String {
        if(hostName.startsWith("http://") || hostName.startsWith("https://")){
            return "$hostName$path"
        } else {
            return "https://$hostName$path"
        }
    }

    private val defaultpolicy = DefaultRetryPolicy(
            30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

    private fun authorizationToken(token: String): Map<String, String> {
        val header = HashMap<String, String>()
        header.put("Authorization", "Bearer $token")
        return header
    }


}