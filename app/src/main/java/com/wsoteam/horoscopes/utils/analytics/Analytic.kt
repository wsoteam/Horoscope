package com.wsoteam.horoscopes.utils.analytics

import com.amplitude.api.Amplitude
import com.amplitude.api.Identify

object Analytic {
    private val make_purchase = "make_purchase"

    private val set_ver = "set_ver"
    private val AB = "AB"

    fun setVersion() {
        Amplitude.getInstance().logEvent(set_ver)
    }

    fun makePurchase() {
        Amplitude.getInstance().logEvent(make_purchase)
    }

    fun setABVersion(version: String) {
        var identify = Identify().setOnce(AB, version)
        Amplitude.getInstance().identify(identify)
    }

}