package com.wsoteam.horoscopes

import androidx.multidex.MultiDexApplication
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        SubscriptionProvider.init(this)
        val config =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yam_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}