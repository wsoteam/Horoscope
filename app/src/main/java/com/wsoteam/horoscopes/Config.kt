package com.wsoteam.horoscopes

import java.util.concurrent.TimeUnit

object Config {
    const val DEFAULT_TIME_NOTIFY = "18:00"
    const val VPN_DATA_URL = "http://37.252.15.110/horo/"
    //const val VPN_DATA_URL = "http://37.252.15.110/horo/"
    //const val VPN_DATA_URL = "https://mobtracks.ru/horo/"
    const val ID_PRICE = "no_ads_start"

    const val OPEN_PREM = "OPEN_PREM"
    const val OPEN_PREM_FROM_MAIN = "OPEN_PREM_FROM_MAIN"
    const val OPEN_PREM_FROM_REG = "OPEN_PREM_FROM_REG"

    const val OPEN_FROM_NOTIFY = "OPEN_FROM_NOTIFY"

    const val ATTEMPTS_FOR_DAY = 100000

    val MILLIS_FOR_NEW_ATTEMPTS = TimeUnit.HOURS.toMillis(24)




}