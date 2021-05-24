package com.wsoteam.horoscopes

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.bugfender.sdk.Bugfender
import com.bugsee.library.serverapi.AmazonService
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.revenuecat.purchases.Purchases
import com.userexperior.UserExperior
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig


class App : MultiDexApplication() {

    @Volatile
    var applicationHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SubscriptionProvider.init(this)
        UserExperior.startRecording(
            getApplicationContext(),
            getString(R.string.release_user_expirior_id)
        );
        //}
        Amplitude.getInstance()
            .initialize(this, getString(R.string.amplitude_id))
            .enableForegroundTracking(this)

        applicationHandler = Handler(applicationContext.mainLooper)

        FacebookSdk.sdkInitialize(this)
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.setAutoLogAppEventsEnabled(true)
        AppEventsLogger.activateApp(applicationContext)

        Purchases.debugLogsEnabled = true
        Purchases.configure(this, "qoEoXUqEEkmhYuONwXDeGutbzHkRSQXt")

        /*// Branch logging for debugging
        Branch.enableLogging()

        // Branch object initialization
        Branch.getAutoInstance(this)*/

        val config =
            YandexMetricaConfig.newConfigBuilder(getString(R.string.yam_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }

    @SuppressLint("NewApi")
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val att = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
            val channelId = "com.wsoteam.horoscopes"
            val channelName = "com.wsoteam.horoscopes"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.lightColor = Color.parseColor("#4B8A08")
            channel.vibrationPattern = longArrayOf(0, 500)
            channel.enableVibration(true)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}