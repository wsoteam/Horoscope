package com.wsoteam.horoscopes

import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.bugfender.sdk.Bugfender
import com.bugsee.library.Bugsee
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.qonversion.android.sdk.Qonversion
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.id.Creator
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : MultiDexApplication() {

    @Volatile
    var applicationHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SubscriptionProvider.init(this)
        val config =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yam_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        //Bugsee.launch(this, "1187e351-e756-4bad-80af-5efa69a3ff56") //wadimkazak@mail.ru

        Amplitude.getInstance()
            .initialize(this, "3031a61ead2f7482d87c899794cec751")
            .enableForegroundTracking(this)

        applicationHandler =  Handler(applicationContext.mainLooper)

        Bugfender.init(this, "nMMITxQP2vlSvBuuHLxNDrQxRpw2r21I", BuildConfig.DEBUG)
        Bugfender.enableCrashReporting()
        Bugfender.enableUIEventLogging(this)
        Bugfender.enableLogcatLogging() // optional, if you want logs automatically collected from logcat

        //Qonversion.initialize(this, "rUC-czxB0dMVbaQHM8LOuRtZ1RQr2BE9", Creator.getId())
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}