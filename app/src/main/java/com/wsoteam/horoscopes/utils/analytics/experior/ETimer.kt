package com.wsoteam.horoscopes.utils.analytics.experior

import android.util.Log
import com.userexperior.UserExperior


object ETimer {

    const val FIRST_SPLASH = "first_splash_loading"
    const val NEXT_SPLASH = "next_splash_loading"

    const val FIRST_LOAD_INTER = "splash_inter_loading"
    const val NEXT_LOAD_INTER = "next_inter_loading"

    const val LOAD_NATIVE = "native_loading"


    fun trackStart(name : String){
        try {
            UserExperior.startTimer(name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackEnd(name : String){
        try {
            UserExperior.endTimer(name)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }
}