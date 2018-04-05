package com.fintechplatform.ui.enterprise.documents.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_enterprise_documents.*
import javax.inject.Inject

class EnterpriseDocumentsActivity: AppCompatActivity(), EnterpriseDocumentsContract.View {

    @Inject lateinit var presenter: EnterpriseDocumentsContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    val PICK_DOCUMENT_PAGES = 110

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterprise_documents)

        EnterpriseDocumentsUI.instance.createEnterpriseDocumentsViewComponent(this, this).inject(this)

        backwardButton.setOnClickListener { presenter.onAbort() }

        confirmButton.setOnClickListener { presenter.onConfirm() }

        insertPage.setOnClickListener { presenter.onInsertPages() }

        presenter.initializate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun onBackPressed() {
        presenter.onAbort()
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
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun showGenericError() {
        alertHelper.internalError(this).show()
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
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this@EnterpriseDocumentsActivity,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this@EnterpriseDocumentsActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this@EnterpriseDocumentsActivity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PICK_DOCUMENT_PAGES)
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, optData: Intent?) {
        super.onActivityResult(requestCode, resultCode, optData)

        if(requestCode == PICK_DOCUMENT_PAGES && resultCode == RESULT_OK){
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
}