package com.dwafintech.dwapay.main.networklist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.android.alert.AlertHelpers
import com.fintechplatform.android.transfer.ui.TransferUI
import javax.inject.Inject


/**
 * Peer to Peer Payment Users list
 */
class NetworkUsersListFragment : Fragment(), NetworkUsersListContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: NetworkUsersListContract.Presenter
    @Inject lateinit var manager: NetworkUsersManager
    @Inject lateinit var transferUI: TransferUI
    //@Inject lateinit var newUserNetworkUI: NewUserNetworkUI

    companion object {
        private val PERMISSION_REQUEST_CODE = 10

        fun newInstance(): P2PFragment {
            return P2PFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(com.dwafintech.dwapay.R.layout.fragment_networklist, container, false)
        //(activity.application as App).netComponent?.inject(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeLayout.setOnRefreshListener { handleRefresh() }

        listView.layoutManager = LinearLayoutManager(activity)
        listView.adapter = NetworkUsersAdapter(activity, manager) { p2puser ->
            val bundle = Bundle()
            bundle.putString("p2pid", p2puser.userid)
            transferUI.start(context, bundle)
        }

        addContact.setOnClickListener {
            // add new contact
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            presenter.reloadP2P()
        }
    }

    override fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(activity,
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

    override fun initAdapter(networkUserModels: List<NetworkUserModel>) {
        manager.initAll(networkUserModels)
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
}
