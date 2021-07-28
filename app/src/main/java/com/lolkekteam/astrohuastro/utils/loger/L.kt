package com.lolkekteam.astrohuastro.utils.loger

import android.util.Log
import com.lolkekteam.astrohuastro.Config

object L {
    fun log(log: String) {
        if (Config.IS_NEED_LOGGING) {
            Log.i("LOL", log)
        }
    }
}