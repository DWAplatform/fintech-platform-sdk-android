package com.fintechplatform.ui.secure3d.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.activity_secure3_d.view.*
import javax.inject.Inject


class Secure3DFragment: Fragment(), Secure3DContract.View {
    @Inject
    lateinit var presenter: Secure3DContract.Presenter

    var navigation: Secure3DContract.Navigator?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.activity_secure3_d, container, false)
        (activity.application as Secure3DUIFactory).create3DComponent(this).inject(this)

        arguments.getString("redirecturl")?.let { presenter.initialize(it) }

        view?.webview?.settings?.javaScriptEnabled = true

        view?.webview?.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                    view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                presenter.onPageStarted()
            }

            override fun onPageFinished(view: WebView, url: String) {
                presenter.onPageFinished(url)
            }
        }

        return view
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