package com.wsoteam.horoscopes

import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.amplitude.api.Amplitude
import com.bugfender.sdk.Bugfender
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.revenuecat.purchases.Purchases
import com.userexperior.UserExperior
import com.wsoteam.horoscopes.utils.SubscriptionProvider


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
    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}