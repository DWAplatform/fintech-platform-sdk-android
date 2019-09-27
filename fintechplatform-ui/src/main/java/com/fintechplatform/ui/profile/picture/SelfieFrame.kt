package com.fintechplatform.ui.profile.picture

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.ui.R
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.fragment_selfie.*
import kotlinx.android.synthetic.main.fragment_selfie.view.*


class SelfieFrame: Fragment() {

    lateinit var hostName: String
    lateinit var dataAccount: DataAccount
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selfie, container, false)
        
        view.icon.setOnClickListener { onChangeSelfiePic() }

        arguments?.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                this.hostName = hostname
                this.dataAccount = dataAccount
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        getPicture()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            PICK_PHOTO_SELFIE -> onActivityResultPickPhotoSelfie(resultCode, data)
        }
    }

    private fun onActivityResultPickPhotoSelfie(resultCode: Int, optdata: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        val data = optdata ?: return

        try {

            val bitmap = data.extras["data"] as Bitmap
            
            FintechPlatformAPI.getProfile(hostName, context)
                    .uploadProfilePicture(token = dataAccount.accessToken, userId = dataAccount.ownerId, tenantId = dataAccount.tenantId, picture = ImageHelper().bitmapToByteArray(bitmap)) { userProfileReply, optError ->
                        optError?.let{ error -> Log.d("Error uploading", error.message)}
                        userProfileReply?.let {
                            Log.d("objectId Selfie", it.photoObjId)
                        }
                    }

        } catch (e: Exception) {
            Log.e("error image thumbnail", "onActivityResultPickPhotoSelfie", e)
        }
    }

    private fun onChangeSelfiePic() {
        val chooserIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(chooserIntent, PICK_PHOTO_SELFIE)
    }

    private fun getPicture() {
        FintechPlatformAPI.getProfile(hostName, context)
                .getProfilePictureFromBucket(token = dataAccount.accessToken, userId = dataAccount.ownerId, tenantId = dataAccount.tenantId) { bytes, optError ->
                    optError?.let { error -> Log.d("Error get", error.message) }
                    bytes?.let { imageBA ->
                        Log.d("image", "${imageBA.size}")
                        ImageHelper().setImageViewUser(icon, imageBA)
                    }
                }
    }

    companion object {
        const val PICK_PHOTO_SELFIE = 1
        fun newInstance(hostName: String, dataAccount: DataAccount): SelfieFrame {
            val frag = SelfieFrame()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}