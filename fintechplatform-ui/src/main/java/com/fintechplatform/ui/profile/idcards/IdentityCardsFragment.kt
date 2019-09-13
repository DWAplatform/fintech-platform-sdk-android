package com.fintechplatform.ui.profile.idcards

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.api.profile.models.DocType
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.idcards.di.IdentityCardsViewComponent
import kotlinx.android.synthetic.main.fragment_profile_ids.*
import kotlinx.android.synthetic.main.fragment_profile_ids.view.*
import javax.inject.Inject


open class IdentityCardsFragment: Fragment(), IdentityCardsContract.View {

    @Inject
    lateinit var presenter: IdentityCardsContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    val PICK_ID_CARD = 10
    val PICK_ID_CARD_FRONT = 20
    val PICK_ID_CARD_BACK = 30

    var navigation: IdentityCardsContract.Navigation? = null

    open fun createIdentityCardsViewComponent(context: Context, view: IdentityCardsContract.View, hostname: String, dataAccount: DataAccount): IdentityCardsViewComponent {
        return IdentityCardsUI.Builder.buildIdCardsViewComponent(context, view, hostname, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_ids, container, false)

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createIdentityCardsViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }
        presenter.initializate()

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        view.identityCardFront.setOnClickListener { presenter.onCameraFrontClick() }

        view.identityCardBack.setOnClickListener { presenter.onCameraBackClick() }

        view.documentChooser.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val docType = tab.tag as DocType
                presenter.onDocTypeSelected(docType)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? IdentityCardsContract.Navigation)?.let {
            navigation = it
        }?: Log.e(IdentityCardsFragment::class.java.canonicalName, "IdentityCardsContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    /** Contract.View */

    override fun addDocumentTypeSelectable(list: List<DocType>) {
        list.forEach {
            when(it){
                DocType.IDENTITY_CARD -> documentChooser.addTab(documentChooser.newTab().setTag(it).setText(R.string.identity_card))
                DocType.DRIVING_LICENCE -> documentChooser.addTab(documentChooser.newTab().setTag(it).setText(R.string.driving_licence))
                DocType.PASSPORT -> documentChooser.addTab(documentChooser.newTab().setTag(it).setText(R.string.passport))
            }
        }
    }
    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun setFrontImage(front: Bitmap?) {
        front.let { identityCardFront.setImageBitmap(it) }
    }

    override fun setBackImage(back: Bitmap?) {
        back.let { identityCardBack.setImageBitmap(it) }
    }

    override fun enableButtons(isEnable: Boolean) {
        identityCardBack.isEnabled = isEnable
        identityCardFront.isEnabled = isEnable
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun showFrontAndBack() {
        frontLayout.visibility = View.VISIBLE
        backLayout.visibility = View.VISIBLE
        frontLayout.layoutParams.width = 0
        labelFrontLayout.visibility =View.VISIBLE
        identityCardFront.setImageResource(R.drawable.ic_idcard_front)
        identityCardBack.setImageResource(R.drawable.ic_idcard_back)
    }

    override fun showOnePicture() {
        frontLayout.visibility = View.VISIBLE
        backLayout.visibility = View.GONE
        frontLayout.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        labelFrontLayout.visibility = View.GONE
        identityCardFront.setImageResource(R.drawable.ic_idcard_front)
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(context) { _, _ ->
            navigation?.goBackFromIdCards()
        }
    }

    override fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PICK_ID_CARD)
            return
        }
    }

    override fun goToCameraFront() {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(chooserIntent, PICK_ID_CARD_FRONT)
    }

    override fun goToCameraBack() {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(chooserIntent, PICK_ID_CARD_BACK)
    }

    override fun goBack() {
        navigation?.goBackFromIdCards()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, optData: Intent?) {
        super.onActivityResult(requestCode, resultCode, optData)

        if(requestCode == PICK_ID_CARD_FRONT && resultCode == AppCompatActivity.RESULT_OK){
            presenter.onPictureTaken(optData, 0)
        }

        if(requestCode == PICK_ID_CARD_BACK && resultCode == AppCompatActivity.RESULT_OK){
            presenter.onPictureTaken(optData, 1)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PICK_ID_CARD ) {
            var cameraok = false
            var externalstorage = false
            for (i in grantResults.indices) {
                if (grantResults[i] >= 0) {
                    if (permissions[i] == Manifest.permission.CAMERA) {
                        cameraok = true
                    }
                    if (permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                        externalstorage = true
                    }
                }
            }
//            fromPermissions = true
//            permissionsOk = cameraok && externalstorage
        }
    }

    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount): IdentityCardsFragment {
            val frag = IdentityCardsFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}