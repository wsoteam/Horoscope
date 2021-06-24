package com.wsoteam.horoscopes.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.android.synthetic.main.form_activity.*

object PreferencesProvider {

    private const val AD = "AD_STATUS"
    private const val PRICE_TAG = "PRICE_TAG"
    private const val DEF_PRICE = "879 RUB"
    private const val NAME_TAG = "NAME_TAG"
    private const val BIRTH_TAG = "BIRTH_TAG"
    private const val NOTIF_STATUS_TAG = "NOTIF_STATUS_TAG"
    private const val NOTIF_TAG = "NOTIF_TAG"
    private const val TEXT_TAG = "TEXT_TAG"
    private const val COUNT_NOTIF = "COUNT_NOTIF"
    private const val VER_TAG = "VER_TAG"
    private const val WHERE_TAG = "WHERE_TAG"
    private const val PREM_SHOW_TAG = "PREM_SHOW_TAG"
    private const val TODAY_NOTIF_TAG = "TODAY_NOTIF_TAG"
    const val DEF_TODAY_NOTIF = -1

    const val DEFAULT_TIME_NOTIFY = "09:00"

    private const val TIME = "TIME"
    private const val ATTEMPTS = "ATTEMPTS"

    private const val ID_TAG = "ID_TAG"
    const val ID_EMPTY = "ID_EMPTY"
    const val FIRST_ENTER_TAG = "FIRST_ENTER_TAG"
    const val SCREEN_URI_TAG = "SCREEN_URI_TAG"
    const val BAN_FREQUENCY_TAG = "BAN_FREQUENCY_TAG"
    const val PRICE_INDEX_TAG = "PRICE_INDEX_TAG"
    const val SHOW_ONBOARD_TAG = "SHOW_ONBOARD_TAG"
    const val IS_SETUPED_TAG = "IS_SETUPED_TAG"
    const val SHOW_PREM_ALERT_TAG = "SHOW_PREM_ALERT_TAG"

    const val INTER_FREQ = "INTER_FREQ"

    const val VALUE_PRICE = "VALUE_PRICE"
    const val UNIT_PRICE = "UNIT_PRICE"

    const val FIRST_ENTER_TIME_TAG = "FIRST_ENTER_TIME_TAG"
    const val FIRST_ENTER_TIME_TRIGGER = 86_400_000L
    const val FIRST_ENTER_SHOW_TAG = "FIRST_ENTER_SHOW_TAG"

    const val USER_META_TAG = "USER_META_TAG"
    const val USER_META_EMPTY = ""

    const val NET_LOST_TAG = "NET_LOST_TAG"

    const val THEME_TAG = "THEME_TAG"
    const val EMPTY_THEME_ID = -1

    const val LAST_FCM_TIME_TAG = "LAST_FCM_TIME_TAG"
    const val AB_FCM_TAG = "AB_FCM_TAG"


    private fun getInstance(): SharedPreferences? {
        val sp = App.getInstance().getSharedPreferences(
            App.getInstance().packageName + ".SharedPreferences",
            Context.MODE_PRIVATE
        )
        return sp
    }

    private fun editor(put: (SharedPreferences.Editor?) -> SharedPreferences.Editor?) =
        put(getInstance()?.edit())?.apply()

    fun setADStatus(isEnabled: Boolean) = editor { it?.putBoolean(AD, isEnabled) }
    fun isADEnabled() = getInstance()?.getBoolean(AD, true) ?: true

    fun setPrice(price: String) = editor { it?.putString(PRICE_TAG, price) }
    fun getPrice() = getInstance()?.getString(PRICE_TAG, DEF_PRICE)

    fun setPriceValue(price: Long) = editor { it?.putLong(VALUE_PRICE, price) }
    fun getPriceValue() = getInstance()?.getLong(VALUE_PRICE, 879_000_000)

    fun setPriceUnit(price: String) = editor { it?.putString(UNIT_PRICE, price) }
    fun getPriceUnit() = getInstance()?.getString(UNIT_PRICE, "RUB")

    fun setName(name: String) = editor { it?.putString(NAME_TAG, name) }
    fun getName() = getInstance()?.getString(NAME_TAG, "")

    fun setBirthday(date: String) {
        L.log("setBirthday $date")
        editor { it?.putString(BIRTH_TAG, date) }
        Analytic.setBirthday(date)
        Analytic.setSign(
            App.getInstance().applicationContext.resources.getStringArray(R.array.names_signs)[choiceSign(
                date
            )]
        )
    }

    fun getBirthday() = getInstance()?.getString(BIRTH_TAG, "")

    fun setNotifStatus(isEnabled: Boolean) = editor { it?.putBoolean(NOTIF_STATUS_TAG, isEnabled) }
    fun getNotifStatus() = getInstance()?.getBoolean(NOTIF_STATUS_TAG, true) ?: true

    fun setNotifTime(time: String) = editor { it?.putString(NOTIF_TAG, time) }
    fun getNotifTime() = getInstance()?.getString(NOTIF_TAG, DEFAULT_TIME_NOTIFY) ?: ""

    fun setLastText(text: String) = editor { it?.putString(TEXT_TAG, text) }
    fun getLastText() = getInstance()?.getString(TEXT_TAG, "") ?: ""

    fun setNotifCount(count: Int) = editor { it?.putInt(COUNT_NOTIF, count) }
    fun getNotifCount() = getInstance()?.getInt(COUNT_NOTIF, 0) ?: 0

    fun setVersion(ver: String) = editor { it?.putString(VER_TAG, ver) }
    fun getVersion() = getInstance()?.getString(VER_TAG, "")

    fun setBeforePremium(where: String) = editor { it?.putString(WHERE_TAG, where) }
    fun getBeforePremium() = getInstance()?.getString(WHERE_TAG, "")

    fun setLastDayNotification(dayOfYear: Int) = editor { it?.putInt(TODAY_NOTIF_TAG, dayOfYear) }
    fun getLastDayNotification() = getInstance()?.getInt(TODAY_NOTIF_TAG, DEF_TODAY_NOTIF)

    private fun setEnterCount(newCount: Int) = editor { it?.putInt(PREM_SHOW_TAG, newCount) }

    fun setPercent(per: Int) = editor {
        it?.putInt(INTER_FREQ, per)
    }

    fun getPercent() = getInstance()?.getInt(INTER_FREQ, 100)

    fun getPremShowPossibility(): Boolean {
        var pastCount = getInstance()?.getInt(PREM_SHOW_TAG, 0)!!
        setEnterCount(pastCount + 1)
        return pastCount == 0 || pastCount == Config.PREM_SHOW_FREQUENCY
    }





    var firstEnter: Int
        get() = getInstance()?.getInt(FIRST_ENTER_TAG, -1)!!
        set(value) = editor { it?.putInt(FIRST_ENTER_TAG, value) }!!

    var userID: String
        get() = getInstance()?.getString(ID_TAG, ID_EMPTY)!!
        set(value) = editor { it?.putString(ID_TAG, value) }!!

    var timeMillis: Long
        get() = getInstance()?.getLong(TIME, 0)!!
        set(value) = editor { it?.putLong(TIME, value) }!!

    var attempts: Int
        get() = getInstance()?.getInt(ATTEMPTS, Config.ATTEMPTS_FOR_DAY)!!
        set(value) = editor { it?.putInt(ATTEMPTS, value) }!!

    var screenURI: String
        get() = getInstance()?.getString(SCREEN_URI_TAG, "")!!
        set(value) = editor { it?.putString(SCREEN_URI_TAG, value) }!!

    var banPercent: Int
        get() = getInstance()?.getInt(BAN_FREQUENCY_TAG, 0)!!
        set(value) = editor { it?.putInt(BAN_FREQUENCY_TAG, value) }!!


    var isShowOnboard: Boolean
        get() = getInstance()?.getBoolean(SHOW_ONBOARD_TAG, false)!!
        set(value) = editor { it?.putBoolean(SHOW_ONBOARD_TAG, value) }!!

    var isShowPremAlert: Boolean
        get() = getInstance()?.getBoolean(SHOW_PREM_ALERT_TAG, false)!!
        set(value) = editor { it?.putBoolean(SHOW_PREM_ALERT_TAG, value) }!!

    var isSetuped: Boolean
        get() = getInstance()?.getBoolean(IS_SETUPED_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_SETUPED_TAG, value) }!!

    var firstEnterTime: Long
        get() = getInstance()?.getLong(FIRST_ENTER_TIME_TAG, -1L)!!
        set(value) = editor { it?.putLong(FIRST_ENTER_TIME_TAG, value) }!!

    var isShowCatPremium: Boolean
        get() = getInstance()?.getBoolean(FIRST_ENTER_SHOW_TAG, false)!!
        set(value) = editor { it?.putBoolean(FIRST_ENTER_SHOW_TAG, value) }!!

    var userMetaData: String
        get() = getInstance()?.getString(USER_META_TAG, USER_META_EMPTY)!!
        set(value) = editor { it?.putString(USER_META_TAG, value) }!!


    var netLostAttempt: Int
        get() = getInstance()?.getInt(NET_LOST_TAG, 0)!!
        set(value) = editor { it?.putInt(NET_LOST_TAG, value) }!!

    var isNeedNewTheme: Boolean
        get() = getInstance()?.getBoolean(THEME_TAG, false)!!
        set(value) = editor { it?.putBoolean(THEME_TAG, value) }!!

    var lastFCMTime: Int
        get() = getInstance()?.getInt(LAST_FCM_TIME_TAG, -1)!!
        set(value) = editor { it?.putInt(LAST_FCM_TIME_TAG, value) }!!

    ////New onboard

    private const val GENDER_TAG = "GENDER_TAG"
    const val FEMALE = 0
    const val MALE = 1

    var userGender: Int
        get() = getInstance()?.getInt(GENDER_TAG, MALE)!!
        set(value) = editor { it?.putInt(GENDER_TAG, value) }!!


    private const val RELATIONSHIP_TAG = "RELATIONSHIP_TAG"
    const val SINGLE = 0
    const val UNSINGLE = 1

    var userRelationship: Int
        get() = getInstance()?.getInt(RELATIONSHIP_TAG, SINGLE)!!
        set(value) = editor { it?.putInt(RELATIONSHIP_TAG, value) }!!


    private const val BIRTHTIME_TAG = "BIRTHTIME_TAG"

    var birthTime: String
        get() = getInstance()?.getString(BIRTHTIME_TAG, "17:33")!!
        set(value) = editor { it?.putString(BIRTHTIME_TAG, value) }!!


    private const val IS_SHOW_REWARD_MAIN_TAG = "IS_SHOW_REWARD_MAIN_TAG"
    var isShowRewardedMain: Boolean
        get() = getInstance()?.getBoolean(IS_SHOW_REWARD_MAIN_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_SHOW_REWARD_MAIN_TAG, value) }!!


    private const val IS_SHOW_REWARD_SCAN_TAG = "IS_SHOW_REWARD_SCAN_TAG"
    var isShowRewardedScan: Boolean
        get() = getInstance()?.getBoolean(IS_SHOW_REWARD_SCAN_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_SHOW_REWARD_SCAN_TAG, value) }!!

    private const val SHOWED_AD_ON_PAIRS_TAG = "SHOWED_AD_ON_PAIRS_TAG"
    const val EMPTY_LIST = ""

    var listShowedSigns: String
        get() = getInstance()?.getString(SHOWED_AD_ON_PAIRS_TAG, EMPTY_LIST)!!
        set(value) = editor { it?.putString(SHOWED_AD_ON_PAIRS_TAG, value) }!!


    private const val ROAD_AFTER_BUY = "ROAD_AFTER_BUY"
    const val DEF_ROAD = 0
    const val MATCH_RESULT_ROAD = 1

    var roadAfterBuy: Int
        get() = getInstance()?.getInt(ROAD_AFTER_BUY, DEF_ROAD)!!
        set(value) = editor { it?.putInt(ROAD_AFTER_BUY, value) }!!

    private const val HAND_INFO_TAG = "HAND_INFO_TAG"
    const val EMPTY_HAND_INFO = -1

    var handInfoIndex: Int
        get() = getInstance()?.getInt(HAND_INFO_TAG, EMPTY_HAND_INFO)!!
        set(value) = editor { it?.putInt(HAND_INFO_TAG, value) }!!

    private const val PLACE_TAG = "PLACE_TAG"

    var userPlace: String
        get() = getInstance()?.getString(PLACE_TAG, "")!!
        set(value) = editor { it?.putString(PLACE_TAG, value) }!!

    private const val IS_NEED_SHOW_INTER_AFTER_ONBOARD_TAG = "IS_NEED_SHOW_INTER_AFTER_ONBOARD_TAG"

    var isNeedShowInterAfterOnboard: Boolean
        get() = getInstance()?.getBoolean(IS_NEED_SHOW_INTER_AFTER_ONBOARD_TAG, false)!!
        set(value) = editor { it?.putBoolean(IS_NEED_SHOW_INTER_AFTER_ONBOARD_TAG, value) }!!


    private const val IS_MULTIPLE_REWARD = "IS_MULTIPLE_REWARD"
    var isMultipleRewardAd: String
        get() = getInstance()?.getString(IS_MULTIPLE_REWARD, "")!!
        set(value) = editor { it?.putString(IS_MULTIPLE_REWARD, value) }!!



    private const val IS_DATE_NEED = "IS_DATE_NEED"
    var isDateNeed: String
        get() = getInstance()?.getString(IS_DATE_NEED, "")!!
        set(value) = editor { it?.putString(IS_DATE_NEED, value) }!!


}