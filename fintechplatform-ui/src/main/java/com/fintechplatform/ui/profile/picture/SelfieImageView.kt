package com.fintechplatform.ui.profile.picture

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.ui.R


class SelfieImageView(context: Context, attributeSet: AttributeSet?): LinearLayout(context, attributeSet) {

    private var imageView: ImageView?= null
    lateinit var hostName: String
    lateinit var ownerId: String
    lateinit var tenantId: String
    lateinit var accessToken: String

    constructor(context: Context): this(context, null)

    init {

        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val inflatedView = inflater.inflate(R.layout.selfie_view, this, true)

        imageView = inflatedView.findViewById(R.id.icon)

//        val a: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SelfieImageView, 0,0)
//
//        hostName = a.getString(R.styleable.SelfieImageView_hostName)?: ""
//        ownerId = a.getString(R.styleable.SelfieImageView_ownerId)?: ""
//        tenantId = a.getString(R.styleable.SelfieImageView_tenantId)?: ""
//        accessToken = a.getString(R.styleable.SelfieImageView_token)?: ""
//
//        a.recycle()

    }

    fun getPhotoProfile() {
        FintechPlatformAPI.getProfile(hostName, context)
            .getProfilePictureFromBucket(token = accessToken, userId = ownerId, tenantId = tenantId) { bytes, optError ->
                optError?.let{ error -> Log.d("Error get", error.message)}
                bytes?.let { imageBA ->
                    Log.d("image", "${imageBA.size}")
                    val optbiu = BitmapFactory.decodeByteArray(imageBA, 0, imageBA.size)
                    optbiu?.let { biu ->
                        imageView?.setImageBitmap(biu)
                    }
                } ?: imageView?.setImageResource(R.drawable.ic_account_circle_black)
            }
    }

    fun setParams(hostName: String, ownerId: String, tenantId: String, token: String){
        this.hostName = hostName
        this.ownerId = ownerId
        this.tenantId = tenantId
        this.accessToken = token

        getPhotoProfile()

    }

}