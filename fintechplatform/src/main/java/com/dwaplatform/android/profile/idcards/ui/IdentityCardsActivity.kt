package com.dwaplatform.android.profile.idcards.ui


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dwaplatform.android.R
import com.dwaplatform.android.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_profile_ids.*
import javax.inject.Inject

class IdentityCardsActivity: AppCompatActivity(), IdentityCardsContract.View {

    @Inject lateinit var presenter: IdentityCardsContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    val PICK_ID_CARD = 10
    val PICK_ID_CARD_FRONT = 20
    val PICK_ID_CARD_BACK = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IdentityCardsUI.instance.createIdCardsViewComponent(this as Context, this).inject(this)

        setContentView(R.layout.activity_profile_ids)

        backwardButton.setOnClickListener { presenter.onAbort() }

        confirmButton.setOnClickListener { presenter.onConfirm() }

        identityCardFront.setOnClickListener { presenter.onCameraFrontClick() }

        identityCardBack.setOnClickListener { presenter.onCameraBackClick() }

        presenter.initializate()

    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun setFrontImage(front: Bitmap?) {
        front.let { idcardFrontPic.setImageBitmap(it) }
    }

    override fun setBackImage(back: Bitmap?) {
        back.let { idcardBackPic.setImageBitmap(it) }
    }

    override fun enableButtons(isEnable: Boolean) {
        identityCardBack.isEnabled = isEnable
        identityCardFront.isEnabled = isEnable
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

    override fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this@IdentityCardsActivity,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this@IdentityCardsActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this@IdentityCardsActivity,
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
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, optData: Intent?) {
        super.onActivityResult(requestCode, resultCode, optData)

        if(requestCode == PICK_ID_CARD_FRONT && resultCode == RESULT_OK){
            presenter.onPictureTaken(optData, 0)
        }

        if(requestCode == PICK_ID_CARD_BACK && resultCode == RESULT_OK){
            presenter.onPictureTaken(optData, 1)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
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
}