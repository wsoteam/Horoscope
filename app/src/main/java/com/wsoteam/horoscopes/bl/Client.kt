package com.wsoteam.horoscopes.bl

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wsoteam.horoscopes.utils.PreferencesProvider

class Client: WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().acceptCookie()
        CookieManager.getInstance().flush()
    }
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (PreferencesProvider.startUrl == ""){
            PreferencesProvider.startUrl
            view!!.loadUrl(url!!)
            Log.e("LOOOL", "url:  $url")
        }

        return true
    }

}