package com.lolkekteam.astrohuastro

import android.os.Handler
import androidx.multidex.MultiDexApplication
import com.qonversion.android.sdk.Qonversion
import com.lolkekteam.astrohuastro.utils.SubscriptionProvider
import com.lolkekteam.astrohuastro.utils.id.Creator

class App : MultiDexApplication() {

    @Volatile
    var applicationHandler: Handler? = null

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        SubscriptionProvider.init(this)

        applicationHandler =  Handler(applicationContext.mainLooper)



        Qonversion.initialize(this, getString(R.string.qonversion_id), Creator.getId())
        //Smartlook.setupAndStartRecording(getString(R.string.smartlock_id))

    }

    companion object {

        private lateinit var sInstance: App

        fun getInstance(): App {
            return sInstance
        }
    }
}