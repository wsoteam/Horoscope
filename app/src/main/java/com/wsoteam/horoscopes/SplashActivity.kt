package com.wsoteam.horoscopes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.notification.AlarmReceiver
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.premium.PremiumHostActivity
import com.wsoteam.horoscopes.utils.AdProvider
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdCallbacks
import com.wsoteam.horoscopes.utils.ads.AdWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    var counter = 0
    var MAX = 2
    var isNextScreenLoading = false


    private fun postGoNext(i: Int) {
        counter += i
        if (counter >= MAX) {
            if (!isNextScreenLoading) {
                isNextScreenLoading = true
                goNext()
            }
        }
    }

    private fun goNext() {
        var intent: Intent
        if (PreferencesProvider.getName() != "" && PreferencesProvider.getBirthday() != "") {
            if(PreferencesProvider.isADEnabled()){
                intent = Intent(this, PremiumHostActivity::class.java).putExtra(Config.OPEN_PREM, Config.OPEN_PREM_FROM_REG)
            }else{
                intent = Intent(this, MainActivity::class.java)
            }
        } else {
            intent = Intent(this, FormActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshNotifications()
        AdWorker.init(this)
        if (PreferencesProvider.isADEnabled()) {
            AdWorker.isNeedShowInter = true
            AdWorker.adCallbacks = object : AdCallbacks {
                override fun onAdClosed() {
                    postGoNext(2)
                    AdWorker.unSubscribe()
                }

                override fun onAdLoaded() {
                    MAX++
                }
            }
        }
        var vm = ViewModelProviders
            .of(this)
            .get(MainVM::class.java)
        vm.preLoadData()
        AdProvider.init(this)
        trackUser()
        CoroutineScope(Dispatchers.IO).launch {
            TimeUnit.SECONDS.sleep(4)
            postGoNext(1)
        }
    }

    private fun refreshNotifications() {
        if (PreferencesProvider.getNotifTime() == PreferencesProvider.DEFAULT_TIME_NOTIFY){
            AlarmReceiver.startNotification(this, 18, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AdWorker.unSubscribe()
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
        mFirebaseAnalytics.logEvent("traffic_id", bundle)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS, bundle)

        postGoNext(1)
    }

    private fun getClickId(s: String): String {
        var id = s
        if (s.contains("&")) {
            id = s.split("&")[0]
        }
        return id
    }
}
