package com.fintechplatform.ui.secure3d.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.fintechplatform.ui.R
import kotlinx.android.synthetic.main.activity_secure3_d.*
import javax.inject.Inject

class Secure3DActivity: AppCompatActivity(), Secure3DContract.View {

    @Inject lateinit var presenter: Secure3DContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as Secure3DUIFactory).create3DComponent(this).inject(this)
        setContentView(R.layout.activity_secure3_d)

        val secureCodeUrl = intent.getStringExtra("redirecturl")

        webview.settings.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                    view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                presenter.onPageStarted()
            }

            override fun onPageFinished(view: WebView, url: String) {
                presenter.onPageFinished(url)
            }
        }

        presenter.initialize(secureCodeUrl)

    }

    override fun loadUrl(url: String) {
        // Q: why use webview.post?
        //webview.post({
        webview.loadUrl(url)
        //})
    }

    override fun showCommunicationWait() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator.visibility = View.GONE
    }

    override fun goBack(){
        finish()
    }
}