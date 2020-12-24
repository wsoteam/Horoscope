package com.wsoteam.horoscopes.utils.analytics.experior

import android.util.Log
import com.userexperior.UserExperior


object Experior {

    private val listHoroPages = listOf("Yesterday ", "Today", "Tommorow", "Week", "Month", "Year")

    private val OPEN_DRAWER = "Burger"
    private val MAIN_PREMIUM = "main_premium"
    private val MAIN_SHARE = "main_share"
    private val MAIN_SHARE_PREFIX = "share_button/"
    private val BURGER_SIGN_PREFIX = "burger_sign/"
    private val BURGER_SETTINGS = "burger_settings "
    private val BURGER_PREMIUM = "burger_premium  "

    fun trackHoroPage(number: Int) {
        try {
            Log.e("LOL", listHoroPages[number])
            UserExperior.startScreen(listHoroPages[number])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackBurgerMenu() {
        try {
            Log.e("LOL", OPEN_DRAWER)
            UserExperior.startScreen(OPEN_DRAWER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackMainPremium() {
        try {
            Log.e("LOL", MAIN_PREMIUM)
            UserExperior.startScreen(MAIN_PREMIUM)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackMainShare() {
        try {
            Log.e("LOL", MAIN_SHARE)
            UserExperior.startScreen(MAIN_SHARE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackMainShareProp(properties : String) {
        try {
            Log.e("LOL", "$MAIN_SHARE_PREFIX$properties")
            UserExperior.startScreen("$MAIN_SHARE_PREFIX$properties")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackBurgerSignProp(properties : String) {
        try {
            Log.e("LOL", "$BURGER_SIGN_PREFIX$properties")
            UserExperior.startScreen("$BURGER_SIGN_PREFIX$properties")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackBurgerSettings() {
        try {
            Log.e("LOL", BURGER_SETTINGS)
            UserExperior.startScreen(BURGER_SETTINGS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun trackBurgerPremium() {
        try {
            Log.e("LOL", BURGER_PREMIUM)
            UserExperior.startScreen(BURGER_PREMIUM)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}