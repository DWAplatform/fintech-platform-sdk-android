package com.fintechplatform.ui.enterprise.documents

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.api.enterprise.models.EnterpriseDocType
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.enterprise.documents.di.EnterpriseDocumentsViewComponent
import com.fintechplatform.ui.enterprise.documents.dialog.EnterpriseDocTypeDialog
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.fragment_enterprise_documents.*
import kotlinx.android.synthetic.main.fragment_enterprise_documents.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
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
        val view = inflater.inflate(R.layout.fragment_enterprise_documents, container, false)


        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createEnterpriseDocumentsViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        view.insertPage.setOnClickListener { presenter.onInsertPages() }
        
        view.docTypePicker.setOnClickListener { showDockTypeDialog() }

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? EnterpriseDocumentsContract.Navigation)?.let {
            navigation = it
        }?: Log.e(EnterpriseDocumentsFragment::class.java.canonicalName, "EnterpriseDocumentsContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    private fun showDockTypeDialog() {
        val dialog = EnterpriseDocTypeDialog.newInstance()
        dialog.show(activity.supportFragmentManager, DIALOG_REQUEST)
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

    override fun setDocTypeSelected(docType: EnterpriseDocType) {
        docTypePickerText.text = docType.toString()
    }

    var photoPath: String? = null

    override fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile = try {
                    // Create an image file name
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd").format(Date())
                    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    /** fixme
                     * https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)
                     *
                     * This method was deprecated in API level 29.
                     * To improve user privacy, direct access to shared/external storage devices is deprecated.
                     * When an app targets Build.VERSION_CODES.Q, the path returned from this method is no longer directly accessible to apps.
                     * Apps can continue to access content stored on shared/external storage by migrating to alternatives such as Context#getExternalFilesDir(String), MediaStore, or Intent#ACTION_OPEN_DOCUMENT.
                     */
                    Log.d("File path created", storageDir.absolutePath)
                    File.createTempFile(
                            "IMG_${timeStamp}",
                            ".jpg",
                            storageDir
                    ).apply {
                        photoPath = absolutePath
                        //galleryAddPic(absolutePath)
                        Log.d("photo path:", photoPath)
                    }

                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(it))
                    startActivityForResult(takePictureIntent, PICK_DOCUMENT_PAGES)
                }
            }
        }
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
            presenter.onPictureTaken(File(photoPath), 0)
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
        const val DIALOG_REQUEST = "docType_request"
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