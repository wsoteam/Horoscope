package com.wsoteam.horoscopes.utils.analytics.experior

import com.userexperior.UserExperior
import com.userexperior.models.recording.enums.UeCustomType


object TagManager {

    fun setCountry(code : String){
        try {
            UserExperior.setCustomTag(code, UeCustomType.TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}