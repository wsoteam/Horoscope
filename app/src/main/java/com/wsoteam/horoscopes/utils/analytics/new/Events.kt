package com.wsoteam.horoscopes.utils.analytics.new

import com.amplitude.api.Amplitude
import org.json.JSONException
import org.json.JSONObject

object Events {


    private const val open_onboard_page = "open_onboard_page"
    private const val page_type = "page_type"
    private const val welcome_page = "welcome_page"
    private const val birth_page = "birth_page"
    private const val name_page = "name_page"
    private const val gender_page = "gender_page"
    private const val time_page = "time_page"
    private const val description_page = "description_page"

    private const val open_splash = "open_splash"

    private const val open_intro_scan_screen = "open_intro_scan_screen"
    private const val skip_onboard_scan_screen = "skip_onboard_scan_screen"

    private const val open_onboard_scan_screen = "open_onboard_scan_screen"

    private const val onboard_perm_scan_success = "onboard_perm_scan_success"
    private const val onboard_perm_scan_failure = "onboard_perm_scan_failure"

    private const val success_onboard_scan = "success_onboard_scan"

    private const val trial = "trial"
    private const val click_button_trial = "click_button_trial"
    private const val open_from = "from"

    private const val open_prem_page = "open_prem_page"
    private const val close_prem_page = "close_prem_page"

    private const val click_button_trial_countdown = "click_button_trial_countdown"
    private const val countdown_trial = "countdown_trial"
    private const val open_countdown = "open_countdown"
    private const val close_countdown = "close_countdown"

    private const val open_page = "open_page"
    private const val page = "page"
    const val main_page = "main_page"
    const val info_page = "info_page"
    const val match_page = "match_page"
    const val scan_page = "scan_page"
    const val settings_page = "settings_page"

    private const val open_sign = "open_sign"
    private const val which_sign = "sign"

    private const val start_rewarded_ad = "start_rewarded_ad"
    private const val end_rewarded_ad = "end_rewarded_ad"
    private const val where_show = "where"
    const val ad_show_main = "main"
    const val ad_show_scan = "scan"
    const val ad_show_match = "match"

    fun openSplash() {
        Amplitude.getInstance().logEvent(open_splash)
    }

    fun openPageOnboard(numberPage: Int) {
        var pageName = when (numberPage) {
            0 -> welcome_page
            1 -> birth_page
            2 -> name_page
            3 -> gender_page
            4 -> time_page
            5 -> description_page
            else -> "unknown_page"
        }
        val eventProperties = JSONObject()
        try {
            eventProperties.put(page_type, pageName)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(open_onboard_page, eventProperties)
    }

    fun openIntroScanScreen() {
        Amplitude.getInstance().logEvent(open_intro_scan_screen)
    }

    fun skipOnboardScanScreen() {
        Amplitude.getInstance().logEvent(skip_onboard_scan_screen)
    }

    fun openOnboardScanScreen() {
        Amplitude.getInstance().logEvent(open_onboard_scan_screen)
    }

    fun successScanPermOnboard() {
        Amplitude.getInstance().logEvent(onboard_perm_scan_success)
    }

    fun failureScanPermOnboard() {
        Amplitude.getInstance().logEvent(onboard_perm_scan_failure)
    }

    fun successOnboardScan() {
        Amplitude.getInstance().logEvent(success_onboard_scan)
    }

    fun openCountDown() {
        Amplitude.getInstance().logEvent(open_countdown)
    }

    fun closeCountDown() {
        Amplitude.getInstance().logEvent(close_countdown)
    }

    fun trialCountDown() {
        Amplitude.getInstance().logEvent(countdown_trial)
    }

    fun clickTrialButtonCountDown() {
        Amplitude.getInstance().logEvent(click_button_trial_countdown)
    }

    fun openPremPage(from: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(open_from, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(open_prem_page, eventProperties)
    }

    fun closePremPage(from: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(open_from, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(close_prem_page, eventProperties)
    }

    fun trial(from: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(open_from, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(trial, eventProperties)
    }

    fun clickButtonTrial(from: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(open_from, from)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(click_button_trial, eventProperties)
    }


    fun openPage(openPage: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(page, openPage)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(open_page, eventProperties)
    }

    fun openSign(signName: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(which_sign, signName)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(open_sign, eventProperties)
    }


    fun startRewAd(where: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(where_show, where)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(start_rewarded_ad, eventProperties)
    }

    fun endRewAd(where: String) {
        val eventProperties = JSONObject()
        try {
            eventProperties.put(where_show, where)
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        Amplitude.getInstance().logEvent(end_rewarded_ad, eventProperties)
    }


}