package com.wsoteam.horoscopes

import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.bugsee.library.Bugsee
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        sInstance = this as App
        SubscriptionProvider.init(this)
        val config =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yam_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        //Bugsee.launch(this, "5dfe90be-88f9-491b-9f76-86370af970b7")

        Amplitude.getInstance()
            .initialize(this, "3031a61ead2f7482d87c899794cec751")
            .enableForegroundTracking(this)

        Amplitude.getInstance().logEvent("EVENT_NAME_HERE")
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}