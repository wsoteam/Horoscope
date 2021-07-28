package com.lolkekteam.astrohuastro.utils.analytics

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

    fun crashAttr() {


    }

    fun start() {

    }


    fun touchBalls() {


    }


    fun showBalls() {


    }

    fun changeSign() {


    }

    fun share(pack : String) {

    }

    fun openSettings() {


    }

    fun setVersion() {

    }

    fun clickAD() {

    }

    fun openFromNotif() {


    }

    fun openFromEveningNotif() {
        //Smartlook.trackCustomEvent(open_from_evening_notif)

    }

    fun showNotif() {
        //Smartlook.trackCustomEvent(show_notif)

    }

    fun showEveningNotif() {
        //Smartlook.trackCustomEvent(show_evening_notif)

    }

    //////////Identify

    fun setBirthday(birth: String) {

    }

    fun setSign(sign: String) {

    }

    fun setABVersion(version: String, priceIndex : Int) {

    }

    /*3 дня:
    - 9.99$
    - 24.99$
    - 49.99$
    - 99.99$*/

    /////////Composite

    fun showHoro(index: Int) {


    }

    fun showPrem(property: String) {


    }

    fun makePurchase(property: String, wherePurchase: String) {


    }




}