package com.tiramakan.simabf.ui.view.dashboards

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

/**
 * Created by Ratan on 7/27/2015.
 */
class Dashboard : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /**
         * Inflate tab_layout and setup Views.
         */
        val view = inflater.inflate(R.layout.webview, null)
        val webView = view.findViewById<View>(R.id.webView1) as WebView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(myPreferences.defaultWebPage)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        return view
        //
        //        String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>";
        //        webView.loadData(customHtml, "text/html", "UTF-8");

    }

    override fun doBack(): Boolean {
        return true
    }

}
