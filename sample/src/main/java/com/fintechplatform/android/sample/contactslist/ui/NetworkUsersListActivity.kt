package com.fintechplatform.android.sample.contactslist.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.fintechplatform.android.FintechPlatform
import com.fintechplatform.android.R
import com.fintechplatform.android.alert.AlertHelpers
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.sample.auth.keys.KeyChain
import com.fintechplatform.android.sample.contactslist.models.NetworkAccounts
import com.fintechplatform.android.sample.contactslist.models.NetworkAccountsManager
import kotlinx.android.synthetic.main.activity_networklist.*
import javax.inject.Inject


/**
 * Peer to Peer Payment Users list
 */
class NetworkUsersListActivity : AppCompatActivity(), NetworkUsersListContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: NetworkUsersListContract.Presenter
    @Inject lateinit var manager: NetworkAccountsManager
   // @Inject lateinit var transferUI: TransferUI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkUsersListUI.instace.buildNetworkListComponent(this, this).inject(this)
        setContentView(R.layout.activity_networklist)
        val hostName = "http://192.168.1.73:9000"
        val userId = "ae86e2bc-db10-4c1e-8a1d-a1f335213477"
        val accountId = "55951c78-7a39-4811-a56c-d60a40a55883"
        val tenantId = "f7569f0e-aaa7-11e7-b71f-ff13485d8836"
        swipeLayout.setOnRefreshListener { handleRefresh() }

        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = NetworkUsersAdapter(this, manager) { p2puser ->

            val bundle = Bundle()
            bundle.putString("p2pid", p2puser.ownerId)
            bundle.putString("p2paccountId", p2puser.accountId)

            FintechPlatform.makeTransfer()
                    .createTrasnferUIComponent(hostName, DataAccount(userId, accountId, tenantId, KeyChain(this)["accessToken"]))
                    .transferUI.start(this, bundle)
        }
    }

    override fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSION_REQUEST_CODE)
        }
    }

    override fun enableRefreshing(isRefreshing: Boolean) {
        swipeLayout.isRefreshing = isRefreshing
    }

    override fun refreshAdapter() {

        listView.adapter.notifyDataSetChanged()
    }

    override fun initAdapter(networkAccounts: List<NetworkAccounts>) {
        manager.initAll(networkAccounts)
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.reloadP2P()
                }
            }
        }
    }

    private fun handleRefresh() {
        presenter.reloadP2P()
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(this).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this) { _,_ ->
            finish()
        }
    }

    //    private fun fetchContacts(): List<String> {
//        val alContacts = ArrayList<String>()
//        val cur = contentResolver.query(
//                ContactsContract.Data.CONTENT_URI, null,
//                ContactsContract.Data.MIMETYPE + "=?",
//                arrayOf(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE),
//                ContactsContract.Data.DISPLAY_NAME)
//        while (cur!!.moveToNext()) {
//            //val name = cur.getString(cur.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))
//            val telephone = cur.getString(cur.getColumnIndex(ContactsContract.Data.DATA1))
//            if (telephone != null && !telephone.isEmpty()) {
//                alContacts.add(telephone)
//            }
//        }
//        cur.close()
//        return alContacts
//
//    }

    companion object {
        private val PERMISSION_REQUEST_CODE = 10
    }
}
