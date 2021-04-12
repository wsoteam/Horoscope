package com.wsoteam.horoscopes.presentation.premium

import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.remote.ABConfig

object SubsIds {

    const val BIG_DIAMOND = "sub_n_diamond_big"
    const val SMALL_DIAMOND = "sub_n_diamond_small"
    const val GIRL = "sub_n_girl"
    const val SPACE = "sub_n_space"
    const val MERMAID = "sub_n_mermaid"
    const val HAIR = "sub_n_hair"
    const val PHONE = "sub_n_phone"


    const val HAND_SCAN = "handscanner_sub"

    fun getId(): String {
        return if (PreferencesProvider.getVersion() == ABConfig.A) {
            SMALL_DIAMOND
        } else {
            BIG_DIAMOND
        }
    }

}