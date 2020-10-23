package com.wsoteam.horoscopes

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.amplitude.api.Amplitude
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.uxcam.UXCam
import com.wsoteam.horoscopes.notification.AlarmReceiver
import com.wsoteam.horoscopes.notification.EveningAlarmReceiver
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.utils.AdProvider
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdCallbacks
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.ads.NativeProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    var counter = 0
    var MAX = 3
    var isNextScreenLoading = false


    private fun postGoNext(i: Int) {
        L.log("postGoNext")
        counter += i
        if (counter >= MAX) {
            if (!isNextScreenLoading) {
                isNextScreenLoading = true
                goNext()
            }
        }
    }

    private fun goNext() {
        L.log("goNext")
        var intent: Intent
        if (PreferencesProvider.getName() != "" && PreferencesProvider.getBirthday() != "") {
            /*if (PreferencesProvider.isADEnabled() && PreferencesProvider.getPremShowPossibility()) {
                intent = Intent(this, PremiumHostActivity::class.java).putExtra(
                    Config.OPEN_PREM,
                    Config.OPEN_PREM_FROM_REG
                )
            } else {*/
            intent = Intent(this, MainActivity::class.java)
            L.log("main activity enter")
            /*}*/
        } else {
            intent = Intent(this, FormActivity::class.java)
            L.log("formActivity enter")
        }
        startActivity(intent)
        finish()
    }

    fun printKeyHash(context: Context) {
        try {
            val info = context.packageManager
                .getPackageInfo(
                    "com.wsoteam.horoscopes",
                    PackageManager.GET_SIGNATURES
                )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "LOL",
                    "KeyHash: " + Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            L.log("${e.localizedMessage}")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            L.log("${e.localizedMessage}")

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindRetention()
        if (BuildConfig.DEBUG) {
            L.log("debug")
            UXCam.startWithKey(getString(R.string.uxcam_id))
        }
        Analytic.start()
        PreferencesProvider.setBeforePremium(Analytic.start_premium)
        NativeProvider.loadNative()
        bindTest()
        refreshNotifications()
        AdWorker.init(this)
        if (PreferencesProvider.isADEnabled() && PreferencesProvider.getBirthday() != "") {
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
        if (intent.getStringExtra(Config.OPEN_FROM_NOTIFY) != null) {
            when (intent.getStringExtra(Config.OPEN_FROM_NOTIFY)) {
                Config.OPEN_FROM_NOTIFY -> {
                    Analytic.openFromNotif()
                    PreferencesProvider.setLastDayNotification(PreferencesProvider.DEF_TODAY_NOTIF)
                }
                Config.OPEN_FROM_EVENING_NOTIF -> Analytic.openFromEveningNotif()
            }
        }
    }

    private fun bindRetention() {
        var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        if (PreferencesProvider.firstEnter == -1){
            PreferencesProvider.firstEnter = currentDay
        }else{
            when(currentDay - PreferencesProvider.firstEnter){
                2 -> FBAnalytic.logTwoDays(this)
                7 -> FBAnalytic.logSevenDays(this)
            }
        }
    }


    private fun bindTest() {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaults(R.xml.default_config)

        firebaseRemoteConfig.fetch(3600).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.activateFetched()
                Amplitude.getInstance().logEvent("norm_ab")
            } else {
                Amplitude.getInstance().logEvent("crash_ab")
            }
            setABTestConfig(firebaseRemoteConfig.getString(ABConfig.REQUEST_STRING))
        }
    }

    private fun setABTestConfig(version: String) {
        L.log("set test")
        PreferencesProvider.setVersion(version)
        Analytic.setABVersion(version)
        Analytic.setVersion()
        postGoNext(1)
    }

    private fun refreshNotifications() {
        if (PreferencesProvider.getNotifTime() == PreferencesProvider.DEFAULT_TIME_NOTIFY && PreferencesProvider.getNotifStatus()) {
            AlarmReceiver.startNotification(this, Config.DEF_HOUR_NOTIF, Config.DEF_MIN_NOTIF)
            EveningAlarmReceiver.startNotification(this, Config.DEF_HOUR_EVENING_NOTIF, Config.DEF_MIN_EVENING_NOTIF)
            L.log("refreshNotifications")
        } else if (PreferencesProvider.getNotifTime() != PreferencesProvider.DEFAULT_TIME_NOTIFY && PreferencesProvider.getNotifStatus()) {
            val (hours, minutes) = PreferencesProvider.getNotifTime().split(":").map { it.toInt() }
            L.log("$hours $minutes refresh")
            AlarmReceiver.startNotification(this, hours, minutes)
            EveningAlarmReceiver.startNotification(this, Config.DEF_HOUR_EVENING_NOTIF, Config.DEF_MIN_EVENING_NOTIF)
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
