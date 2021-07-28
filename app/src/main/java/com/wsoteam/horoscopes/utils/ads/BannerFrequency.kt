package com.wsoteam.horoscopes.utils.ads

import android.util.Log
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.utils.PreferencesProvider
import kotlin.random.Random

object BannerFrequency {
    private var hasRequest = false

    fun runSetup() {
        if (!hasRequest && Config.NEED_LOAD) {
            hasRequest = true
            requestPercent {
                Log.e("LOL", it.toString()  +"dfg ")
                PreferencesProvider.banPercent = it
            }
        }
    }

    private fun requestPercent(onResult: (Int) -> Unit) {

    }

    fun needShow() : Boolean{
        return Random.nextInt(100) < PreferencesProvider.banPercent
    }
}