package com.wsoteam.horoscopes.utils.remote

import com.wsoteam.horoscopes.utils.PreferencesProvider

object ABDate {

    fun isDateNeed(): Boolean = PreferencesProvider.isDateNeed == ABConfig.DATE_NEED
}