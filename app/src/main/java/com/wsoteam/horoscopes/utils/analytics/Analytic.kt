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
        //Smartlook.trackCustomEvent(START)
        YandexMetrica.reportEvent(START)
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
        //Smartlook.trackCustomEvent(set_ver)
        YandexMetrica.reportEvent(set_ver)
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
        //Smartlook.trackCustomEvent(show_notif)
        try {
            UserExperior.setCustomTag(show_notif, UeCustomType.EVENT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
            else -> "error"
        }
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




}