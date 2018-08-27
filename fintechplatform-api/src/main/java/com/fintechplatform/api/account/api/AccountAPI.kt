package com.fintechplatform.api.account.api

import com.android.volley.Request
import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.account.models.AccountLevel
import com.fintechplatform.api.account.models.AccountLevelStatus
import com.fintechplatform.api.account.models.PersonalAccount
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequest
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import org.json.JSONObject
import javax.inject.Inject

open
class AccountAPI @Inject constructor(internal val hostName: String,
                                     internal val queue: IRequestQueue,
                                     internal val requestProvider: IRequestProvider,
                                     internal val log: Log,
                                     internal val netHelper: NetHelper) {

    private val TAG = "AccountAPI"

    open
    fun getPersonalAccount(token: String,
                                   account: Account,
                                   completion: (PersonalAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/${account.tenantId}/${netHelper.getPathFromAccountType(account.accountType)}/${account.ownerId}/accounts/${account.accountId}")
        var request: IRequest<*>?
        try {
            val r = requestProvider.jsonObjectRequest(Request.Method.GET, url, null,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val personalAccount = PersonalAccount(
                        account,
                        AccountLevelStatus.valueOf(response.getString("levelStatus")),
                        AccountLevel.valueOf(response.getString("level")))

                completion(personalAccount, null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "kycRequired", e)
            request = null
        }

        return request
    }

    open
    fun updatePersonalAccount(token: String,
                                      account: Account,
                                      completion: (PersonalAccount?, Exception?) -> Unit): IRequest<*>? {
        val url = netHelper.getURL("/rest/v1/fintech/tenants/${account.tenantId}/${netHelper.getPathFromAccountType(account.accountType)}/${account.ownerId}/accounts/${account.accountId}")
        var request: IRequest<*>?
        try {

            val jsonObject = JSONObject()
            jsonObject.put("levelStatus", AccountLevelStatus.REQUEST_UPGRADE_TO_LEVEL2.name)
            jsonObject.put("status", "ENABLED")

            val r = requestProvider.jsonObjectRequest(Request.Method.PUT, url, jsonObject,
                    netHelper.getHeaderBuilder().authorizationToken(token).getHeaderMap(), { response ->

                val personalAccount = PersonalAccount(
                        account,
                        AccountLevelStatus.valueOf(response.getString("levelStatus")),
                        AccountLevel.valueOf(response.getString("level")))

                completion(personalAccount, null)
            }) { error ->
                completion(null, netHelper.createRequestError(error))
            }
            r.setIRetryPolicy(netHelper.defaultpolicy)
            queue.add(r)
            request = r
        } catch (e: Exception) {
            log.error(TAG, "kycRequired", e)
            request = null
        }

        return request
    }

}