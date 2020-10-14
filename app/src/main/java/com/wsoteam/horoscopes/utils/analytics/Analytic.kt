package com.wsoteam.horoscopes.utils.analytics

import android.util.Log
import com.amplitude.api.Amplitude
import com.amplitude.api.Identify
import org.json.JSONException
import org.json.JSONObject

object Analytic {
    private val make_purchase = "make_purchase"
    private val ad_click = "ad_click"
    private val open_from_notif = "open_from_notif"
    private val show_notif = "show_notif"

    private val set_ver = "set_ver"
    private val AB = "AB"

    //New analytics consts
    private val BIRTHDAY = "birthday"
    private val date = "date"

    private val SIGN = "sign"
    private val zodiac = "zodiac"

    private val HOROSCOPE = "horoscope"
    private val horoscope_type = "horoscope_type"
    private val yesterday = "yesterday"
    private val today = "today"
    private val tommorow = "tommorow"
    private val week = "week"
    private val month = "month"
    private val year = "year"


    private val PREMIUM_PAGE = "premium_page"
    private val premium_page_from = "from"
    val start_premium = "start"
    val form_premium = "after_form"
    val ball_premium = "ball"
    val settings_premium = "settings"
    val crown_premium = "crown"
    val burger_premium = "burger"
    val nav_premium = "nav"
    val month_premium = "month"
    val year_premium = "year"
    val love_premium = "love"
    val ball_alert_premium = "ball_alert"


    private val PREMIUM_TRIAL = "premium_trial_make"
    private val trial_from = "from"
    private val where = "where"
    val form = "form"
    val main = "main"
    private val OTHER_SIGN = "other_sign"
    private val SHARE_SOCIAL = "share_social"
    private val SETTINGS = "settings_page"


    private val BALL_PAGE = "ball_page"
    private val ASK_BALL = "ask_ball"
    private val START = "start"

    fun start() {
        Amplitude.getInstance().logEvent(START)
    }


    fun touchBalls() {
        Amplitude.getInstance().logEvent(ASK_BALL)
    }


    fun showBalls() {
        Amplitude.getInstance().logEvent(BALL_PAGE)
    }


///////////////////New analytics

    fun setBirthday(birth: String) {
        Log.e("LOL", birth)
        var identify = Identify().set(BIRTHDAY, birth)
        Amplitude.getInstance().identify(identify)
    }

    fun setSign(sign: String) {
        Log.e("LOL", sign)
        var identify = Identify().set(SIGN, sign)
        Amplitude.getInstance().identify(identify)
    }

    fun showHoro(index: Int) {
        var property = when (index) {
            0 -> yesterday
            1 -> today
            2 -> tommorow
            3 -> week
            4 -> month
            5 -> year
            else -> "error"
        }
        val eventProperties = JSONObject()
        try {
            eventProperties.put(horoscope_type, property)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(HOROSCOPE, eventProperties)
    }

    fun showPrem(property: String) {
        Log.e("LOL", "showPrem")
        val eventProperties = JSONObject()
        try {
            eventProperties.put(premium_page_from, property)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(PREMIUM_PAGE, eventProperties)
    }

    fun makePurchase(property: String, wherePurchase: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(trial_from, property)
            eventProperties.put(where, wherePurchase)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(PREMIUM_TRIAL, eventProperties)
        Log.e("LOL", "$property -- $wherePurchase")
    }

    fun changeSign() {
        Amplitude.getInstance().logEvent(OTHER_SIGN)
    }

    fun share() {
        Amplitude.getInstance().logEvent(SHARE_SOCIAL)
    }

    fun openSettings() {
        Amplitude.getInstance().logEvent(SETTINGS)
    }

    //////////////////////////////////////////////////////////////////////
    fun setVersion() {
        Amplitude.getInstance().logEvent(set_ver)
    }

    fun makePurchase() {
        Amplitude.getInstance().logEvent(make_purchase)
    }

    fun clickAD() {
        Amplitude.getInstance().logEvent(ad_click)
    }

    fun openFromNotif() {
        Amplitude.getInstance().logEvent(open_from_notif)
    }

    fun showNotif() {
        Amplitude.getInstance().logEvent(show_notif)
    }

    fun setABVersion(version: String) {
        var identify = Identify().set(AB, version)
        Amplitude.getInstance().identify(identify)
    }

}