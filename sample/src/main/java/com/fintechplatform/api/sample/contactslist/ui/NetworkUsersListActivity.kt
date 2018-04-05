package com.fintechplatform.api.sample.contactslist.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.fintechplatform.api.R
import com.fintechplatform.api.sample.MainActivity
import com.fintechplatform.api.sample.auth.keys.KeyChain
import com.fintechplatform.api.sample.contactslist.models.NetworkAccounts
import com.fintechplatform.api.sample.contactslist.models.NetworkAccountsManager
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

        swipeLayout.setOnRefreshListener { handleRefresh() }

        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = NetworkUsersAdapter(this, manager) { p2puser ->

            val bundle = Bundle()
            bundle.putString("p2pid", p2puser.ownerId)
            bundle.putString("p2pAccountId", p2puser.accountId)
            bundle.putString("p2pTenantId", p2puser.tenantId)

            FintechPlatform.buildTransfer()
                    .createTrasnferUIComponent(MainActivity.hostName, DataAccount(MainActivity.ownerId, MainActivity.accountId, "", MainActivity.tenantId, KeyChain(this)["accessToken"]))
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
