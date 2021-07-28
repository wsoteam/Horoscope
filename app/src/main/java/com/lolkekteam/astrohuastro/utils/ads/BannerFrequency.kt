package com.lolkekteam.astrohuastro.utils.ads

import android.util.Log
import com.lolkekteam.astrohuastro.Config
import com.lolkekteam.astrohuastro.utils.PreferencesProvider
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