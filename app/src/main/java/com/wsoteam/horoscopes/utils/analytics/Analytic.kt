package com.wsoteam.horoscopes.utils.analytics

import android.util.Log
import com.amplitude.api.Amplitude
import com.amplitude.api.Identify
import com.userexperior.UserExperior
import com.userexperior.models.recording.enums.UeCustomType
import com.wsoteam.horoscopes.utils.analytics.experior.Experior
import com.yandex.metrica.YandexMetrica
import org.json.JSONException
import org.json.JSONObject


object Analytic {
    private val make_purchase = "make_purchase"
    private val ad_click = "ad_click"
    private val open_from_notif = "open_from_notif"
    private val open_from_evening_notif = "open_from_evening_notif"
    private val show_notif = "show_notif"
    private val show_evening_notif = "show_evening_notif"

    private val open_form = "open_form"
    private val open_main = "open_main"
    private val ad_show = "ad_show"

    private val set_ver = "set_ver"
    private val AB = "AB"
    private val PRICE = "PRICE"

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

    private val love = "love"
    private val career = "career"
    private val money = "money"
    private val health = "health"


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
    private val id = "package"
    private val SETTINGS = "settings_page"


    private val BALL_PAGE = "ball_page"
    private val ASK_BALL = "ask_ball"
    private val START = "start"
    private val CRASH_ATTR = "CRASH_ATTR"

    val FREQUENCY_TAG = "FREQUENCY"

    private const val placement_error_timer = "placement"
    private const val type_error_timer = "type"
    private const val when_error_timer = "when"
    private const val TIMER_ERROR = "timer_error"

    private const val TIMER_END = "end_timer"
    private const val timer_type = "type"
    private const val timer_time = "time"

    private const val NET_LOST_ATTEMPT = "attempt"

    private const val OPEN_ASTRO = "open_astro"
    private const val OPEN_ASTRO_FROM = "from"

    private const val OPEN_ASTRO_SECOND = "open_astro_second"
    private const val OPEN_ASTRO_URL = "open_astro_url"

    private const val FIRST_START = "first_start"
    private const val FIRST_SET_VER = "first_set_ver"

    private const val ONBOARD_ENTER = "premium_onboard_enter"
    private const val ONBOARD_TERMS = "onboard_terms"
    private const val ONBOARD_PRIVACY = "onboard_privacy"
    private const val ONBOARD_FIN = "onboard_finish"

    fun openAstro(from : String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(OPEN_ASTRO_FROM, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(OPEN_ASTRO, eventProperties)
    }

    fun openSecondAstro(from : String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(OPEN_ASTRO_FROM, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(OPEN_ASTRO_SECOND, eventProperties)
    }

    fun openAstroUrl(from : String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(OPEN_ASTRO_FROM, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(OPEN_ASTRO_URL, eventProperties)
    }


    fun crashAttr() {
        Amplitude.getInstance().logEvent(CRASH_ATTR)
        //Smartlook.trackCustomEvent(CRASH_ATTR)
        try {
            UserExperior.setCustomTag(CRASH_ATTR, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun start() {
        Amplitude.getInstance().logEvent(START)
        YandexMetrica.reportEvent(START)
        //Smartlook.trackCustomEvent(START)
        try {
            UserExperior.setCustomTag(START, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun touchBalls() {
        Amplitude.getInstance().logEvent(ASK_BALL)
        //Smartlook.trackCustomEvent(ASK_BALL)
        try {
            UserExperior.setCustomTag(ASK_BALL, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun firstStart() {
        Amplitude.getInstance().logEvent(FIRST_START)
        Log.e("LOL", "firstStart")
    }

    fun firstSetVer() {
        Amplitude.getInstance().logEvent(FIRST_SET_VER)
        Log.e("LOL", "firstSetVer")

    }


    fun enter() {
        Amplitude.getInstance().logEvent(ONBOARD_ENTER)
        Log.e("LOL", "enter")

    }

    fun terms() {
        Amplitude.getInstance().logEvent(ONBOARD_TERMS)
        Log.e("LOL", "terms")

    }

    fun privacy() {
        Amplitude.getInstance().logEvent(ONBOARD_PRIVACY)
        Log.e("LOL", "privacy")

    }

    fun fin() {
        Amplitude.getInstance().logEvent(ONBOARD_FIN)
        Log.e("LOL", "fin")

    }


    fun showBalls() {
        Amplitude.getInstance().logEvent(BALL_PAGE)
        //Smartlook.trackCustomEvent(BALL_PAGE)
        try {
            UserExperior.setCustomTag(BALL_PAGE, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun changeSign() {
        Amplitude.getInstance().logEvent(OTHER_SIGN)
        //Smartlook.trackCustomEvent(OTHER_SIGN)
        try {
            UserExperior.setCustomTag(OTHER_SIGN, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun share(pack : String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(id, pack)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }

        Amplitude.getInstance().logEvent(SHARE_SOCIAL, eventProperties)
        //Smartlook.trackCustomEvent(SHARE_SOCIAL, eventProperties)

        try {
            UserExperior.setCustomTag("$SHARE_SOCIAL/$id - $pack", UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Experior.trackMainShareProp(pack)
    }

    fun openSettings() {
        Amplitude.getInstance().logEvent(SETTINGS)
        //Smartlook.trackCustomEvent(SETTINGS)
        try {
            UserExperior.setCustomTag(SETTINGS, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setVersion() {
        Amplitude.getInstance().logEvent(set_ver)
        YandexMetrica.reportEvent(set_ver)
        //Smartlook.trackCustomEvent(set_ver)
        try {
            UserExperior.setCustomTag(set_ver, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clickAD() {
        Amplitude.getInstance().logEvent(ad_click)
        //Smartlook.trackCustomEvent(ad_click)

    }

    fun openFromNotif() {
        Amplitude.getInstance().logEvent(open_from_notif)
        //Smartlook.trackCustomEvent(open_from_notif)
        try {
            UserExperior.setCustomTag(open_from_notif, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openFromEveningNotif() {
        Amplitude.getInstance().logEvent(open_from_evening_notif)
        //Smartlook.trackCustomEvent(open_from_evening_notif)
        try {
            UserExperior.setCustomTag(open_from_evening_notif, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showNotif() {
        Amplitude.getInstance().logEvent(show_notif)
    }

    fun openForm() {
        Amplitude.getInstance().logEvent(open_form)
    }

    fun openMain() {
        Amplitude.getInstance().logEvent(open_main)
    }

    fun showAd() {
        Amplitude.getInstance().logEvent(ad_show)
    }

    fun showEveningNotif() {
        Amplitude.getInstance().logEvent(show_evening_notif)
        //Smartlook.trackCustomEvent(show_evening_notif)
        try {
            UserExperior.setCustomTag(show_evening_notif, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //////////Identify

    fun setBirthday(birth: String) {
        var identify = Identify().set(BIRTHDAY, birth)
        Amplitude.getInstance().identify(identify)
    }

    fun setSign(sign: String) {
        var identify = Identify().set(SIGN, sign)
        Amplitude.getInstance().identify(identify)
    }

    fun setABVersion(version: String, priceIndex : Int) {
        var identify = Identify().set(AB, version).set(PRICE, priceIndex)
        Amplitude.getInstance().identify(identify)
    }

    fun setNetLostAttempt(count : Int) {
        var identify = Identify().set(NET_LOST_ATTEMPT, count.toString())
        Amplitude.getInstance().identify(identify)
    }

    /*3 дня:
    - 9.99$
    - 24.99$
    - 49.99$
    - 99.99$*/

    /////////Composite

    fun showHoro(index: Int) {
        var property = when (index) {
            0 -> yesterday
            1 -> today
            2 -> tommorow
            3 -> week
            4 -> month
            5 -> year
            6 -> love
            7 -> career
            8 -> health
            9 -> money
            else -> "error"
        }
        Log.e("LOL", "horo -- $property")
        val eventProperties = JSONObject()
        try {
            eventProperties.put(horoscope_type, property)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(HOROSCOPE, eventProperties)
        //Smartlook.trackCustomEvent(HOROSCOPE, eventProperties)

        try {
            UserExperior.setCustomTag("$HOROSCOPE/$horoscope_type - $property", UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
        //Smartlook.trackCustomEvent(PREMIUM_PAGE, eventProperties)
        try {
            UserExperior.setCustomTag("$PREMIUM_PAGE/$premium_page_from - $property", UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun makePurchaseFromOnboard(property: String) {
        Log.e("LOL", "showPrem")
        val eventProperties = JSONObject()
        try {
            eventProperties.put("onboard_screen", property)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent("ONBOARD_PURCHASE", eventProperties)
        //Smartlook.trackCustomEvent(PREMIUM_PAGE, eventProperties)

        try {
            UserExperior.setCustomTag(
                "ONBOARD_PURCHASE/onboard_screen - $property",
                UeCustomType.EVENT
            )
        } catch (e: Exception) {
            e.printStackTrace()

        }
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
        YandexMetrica.reportEvent("trial")
        YandexMetrica.reportEvent("trial_make")
        //Smartlook.trackCustomEvent(PREMIUM_TRIAL, eventProperties)
        try {
            UserExperior.setCustomTag(
                "$PREMIUM_TRIAL/ $trial_from - $property , $where - $wherePurchase",
                UeCustomType.EVENT
            )
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    fun setFrequency(per: Int) {
        var identify = Identify()
            .set(FREQUENCY_TAG, per.toString())
        Amplitude
            .getInstance()
            .identify(identify)
    }

    fun sendErrorTimer(placement: String, type : String, whenPlacement : String){
        val eventProperties = JSONObject()
        try {
            eventProperties.put(placement_error_timer, placement)
            eventProperties.put(type_error_timer, type)
            eventProperties.put(when_error_timer, whenPlacement)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(TIMER_ERROR, eventProperties)
    }

    fun trackTime(type: String, time : String){
        val eventProperties = JSONObject()
        try {
            eventProperties.put(timer_type, type)
            eventProperties.put(timer_time, time)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(TIMER_END, eventProperties)
    }



}