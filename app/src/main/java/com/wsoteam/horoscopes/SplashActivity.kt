package com.wsoteam.horoscopes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.FormActivity
import com.wsoteam.horoscopes.utils.AdProvider
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.net.RepositoryGets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    var counter = 0
    val MAX = 1

    private var job : Job? = null

    private fun goNext(){
        counter++
        if (counter >= MAX){
            var intent = Intent()
            if(PreferencesProvider.getName() != "" && PreferencesProvider.getBirthday() != ""){
                intent = Intent(this, MainActivity::class.java)
            }else{
                intent = Intent(this, FormActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = getData()
            Log.e("LOL", result.size.toString())
        }
        AdProvider.init(this)
        trackUser()
    }

    private suspend fun getData(): List<Sign> {
        return RepositoryGets.getAPI().getData().await()
    }

    private fun trackUser() {
        var client = InstallReferrerClient.newBuilder(this).build()
        client.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> sendAnal(client.installReferrer.installReferrer)
                    InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> sendAnal("DEVELOPER_ERROR")
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> sendAnal(
                        "FEATURE_NOT_SUPPORTED"
                    )
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> sendAnal("SERVICE_DISCONNECTED")
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> sendAnal("SERVICE_UNAVAILABLE")
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                sendAnal("onInstallReferrerServiceDisconnected")
            }
        })
    }

    private fun sendAnal(s: String) {
        val clickId = getClickId(s)
        var mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        var bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, clickId)
        bundle.putString(FirebaseAnalytics.Param.MEDIUM, clickId)
        bundle.putString(FirebaseAnalytics.Param.SOURCE, clickId)
        bundle.putString(FirebaseAnalytics.Param.ACLID, clickId)
        bundle.putString(FirebaseAnalytics.Param.CONTENT, clickId)
        bundle.putString(FirebaseAnalytics.Param.CP1, clickId)
        bundle.putString(FirebaseAnalytics.Param.VALUE, clickId)
        mFirebaseAnalytics!!.logEvent("traffic_id", bundle)
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS, bundle)

        //goNext()
    }

    private fun getClickId(s: String): String {
        var id = s
        if (s.contains("&")) {
            id = s.split("&")[0]
        }
        return id
    }
}
