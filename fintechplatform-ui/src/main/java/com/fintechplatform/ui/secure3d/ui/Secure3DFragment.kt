package com.fintechplatform.ui.secure3d.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.fragment_secure3_d.view.*
import javax.inject.Inject


open class Secure3DFragment: Fragment(), Secure3DContract.View {
    @Inject
    lateinit var presenter: Secure3DContract.Presenter

    var navigation: Secure3DContract.Navigation?=null

    open fun create3DComponent(view: Secure3DContract.View): Secure3DViewComponent {
        return Secure3DUI.Builder.build3DsecureComponent(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_secure3_d, container, false)

        create3DComponent(this).inject(this)

        view.webview.settings.javaScriptEnabled = true

        view.webview.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                    view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                presenter.onPageStarted()
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("URL_pagefinished", url)
                presenter.onPageFinished(url)
            }
        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.getString("redirecturl")?.let {
            presenter.initialize(it)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is Secure3DContract.Navigation) {
            navigation = context
        } else {
            Log.e(Secure3DFragment::class.java.canonicalName, "Secure3DContract.Navigation Interface not implemented in your Activity!!")
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun loadUrl(url: String) {
        // Q: why use webview.post?
        //webview.post({
        view?.webview?.loadUrl(url)
        //})
    }

    override fun showCommunicationWait() {
        view?.activityIndicator?.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        view?.activityIndicator?.visibility = View.GONE
    }

    override fun goBack() {
        navigation?.goBackwardFrom3dSecure()
    }

    companion object {
        fun newInstance(redirectUrl: String): Secure3DFragment {
            val frag = Secure3DFragment()
            val args = Bundle()
            args.putString("redirecturl", redirectUrl)
            frag.arguments = args
            return frag
        }
    }
}