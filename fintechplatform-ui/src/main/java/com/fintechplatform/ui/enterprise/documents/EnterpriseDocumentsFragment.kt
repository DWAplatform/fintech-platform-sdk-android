package com.fintechplatform.ui.enterprise.documents

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.enterprise.documents.di.EnterpriseDocumentsViewComponent
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.activity_enterprise_documents.*
import kotlinx.android.synthetic.main.activity_enterprise_documents.view.*
import javax.inject.Inject


open class EnterpriseDocumentsFragment: Fragment(), EnterpriseDocumentsContract.View {

    @Inject
    lateinit var presenter: EnterpriseDocumentsContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    val PICK_DOCUMENT_PAGES = 110
    var navigation: EnterpriseDocumentsContract.Navigation? = null

    open fun createEnterpriseDocumentsViewComponent(context: Context, view: EnterpriseDocumentsContract.View, hostName: String, dataAccount: DataAccount) : EnterpriseDocumentsViewComponent {
        return EnterpriseDocumentsUI.Builder.buildEnterpriseDocumentsViewComponent(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_enterprise_documents, container, false)


        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createEnterpriseDocumentsViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        view.insertPage.setOnClickListener { presenter.onInsertPages() }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initializate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
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

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(context) { _, _ ->
            navigation?.backFromEnterpriseDocuments()
        }
    }

    override fun showGenericError() {
        alertHelper.internalError(context).show()
    }

    override fun setNumberPages(number: Int) {
        fileIcon.visibility = View.GONE
        numberPages.text = number.toString()
    }

    override fun goToCamera() {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(chooserIntent, PICK_DOCUMENT_PAGES)
    }

    override fun goBack() {
        navigation?.backFromEnterpriseDocuments()
    }

    override fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PICK_DOCUMENT_PAGES)
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, optData: Intent?) {
        super.onActivityResult(requestCode, resultCode, optData)

        if(requestCode == PICK_DOCUMENT_PAGES && resultCode == AppCompatActivity.RESULT_OK){
            presenter.onPictureTaken(optData, 0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PICK_DOCUMENT_PAGES ) {
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
        fun newInstance(hostName: String, dataAccount: DataAccount): EnterpriseDocumentsFragment{
            val frag = EnterpriseDocumentsFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}