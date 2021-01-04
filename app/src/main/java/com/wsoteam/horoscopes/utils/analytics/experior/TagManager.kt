package com.wsoteam.horoscopes.utils.analytics.experior

import android.util.Log
import com.userexperior.UserExperior
import com.userexperior.models.recording.enums.UeCustomType


object TagManager {

    fun setCountry(code : String){
        Log.e("LOL", code)
        try {
            UserExperior.setCustomTag("CC$code", UeCustomType.TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}