package com.wsoteam.horoscopes.bl

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.FrameLayout
import com.google.android.gms.common.api.Api
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.activity_bl.*

class BlActivity : AppCompatActivity(R.layout.activity_bl) {

    lateinit var webBlack: WebView

    private val URLL = "https://www.youtube.com"  //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUI()
       // NotifManager.setAlarm(this)

        webBlack.settings.allowFileAccess = true
        webBlack.settings.allowFileAccess = true
        webBlack.settings.allowContentAccess = true
        webBlack.settings.supportZoom()
        webBlack.settings.useWideViewPort = true

        webBlack.settings.javaScriptEnabled = true
        webBlack.settings.domStorageEnabled = true
        webBlack.settings.userAgentString =
            webBlack.settings.userAgentString + "MobileAppClient/Android/0.9"
        webBlack.webViewClient = Client()


        if (savedInstanceState == null) {
            if (PreferencesProvider.lastUrl == "") {
                var url = PreferencesProvider.url
                webBlack.loadUrl(url)//URLL//url
            } else {
                var url = PreferencesProvider.lastUrl
                webBlack.loadUrl(url)//URLL//url
            }
        }
    }
   /* override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        var results = arrayOf(Uri.parse(data!!.dataString))
        mUploadMessage!!.onReceiveValue(results)
        super.onActivityResult(requestCode, resultCode, data)
    }*/

    private fun createUI() {
        webBlack = WebView(this)

        webBlack.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        fl_black_activity.addView(webBlack)
    }
    override fun onBackPressed() {
        if (webBlack.canGoBack()) {
            webBlack.goBack()
        } else {
            super.onBackPressed()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webBlack.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webBlack.restoreState(savedInstanceState)
    }
}
