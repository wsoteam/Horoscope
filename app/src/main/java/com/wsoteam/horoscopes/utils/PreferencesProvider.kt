package com.wsoteam.horoscopes.utils

import android.content.Context
import android.content.SharedPreferences
import com.wsoteam.horoscopes.App

object PreferencesProvider {

    private const val AD = "AD_STATUS"
    private const val PRICE_TAG = "PRICE_TAG"
    private const val DEF_PRICE = "280 RUB"
    private const val NAME_TAG = "NAME_TAG"
    private const val BIRTH_TAG = "BIRTH_TAG"
    private const val NOTIF_STATUS_TAG = "NOTIF_STATUS_TAG"
    private const val NOTIF_TAG = "NOTIF_TAG"
    private const val TEXT_TAG = "TEXT_TAG"

    const val DEFAULT_TIME_NOTIFY = "18:00 PM"


    private fun getInstance(): SharedPreferences? {
        val sp =  App.getInstance().getSharedPreferences(
            App.getInstance().packageName + ".SharedPreferences",
            Context.MODE_PRIVATE
        )
        return sp
    }

    private fun editor(put: (SharedPreferences.Editor?) -> SharedPreferences.Editor?) = put(getInstance()?.edit())?.apply()

    fun setADStatus(isEnabled: Boolean) = editor { it?.putBoolean(AD, isEnabled) }
    fun isADEnabled() = getInstance()?.getBoolean(AD, true) ?: true

    fun setPrice(price: String) = editor { it?.putString(PRICE_TAG, price)}
    fun getPrice() = getInstance()?.getString(PRICE_TAG, DEF_PRICE)

    fun setName(name: String) = editor { it?.putString(NAME_TAG, name)}
    fun getName() = getInstance()?.getString(NAME_TAG, "")

    fun setBirthday(date: String) = editor { it?.putString(BIRTH_TAG, date)}
    fun getBirthday() = getInstance()?.getString(BIRTH_TAG, "")

    fun setNotifStatus(isEnabled: Boolean) = editor { it?.putBoolean(NOTIF_STATUS_TAG, isEnabled)}
    fun getNotifStatus() = getInstance()?.getBoolean(NOTIF_STATUS_TAG, true) ?: true

    fun setNotifTime(time: String) = editor { it?.putString(NOTIF_TAG, time)}
    fun getNotifTime() = getInstance()?.getString(NOTIF_TAG, DEFAULT_TIME_NOTIFY) ?: ""

    fun setLastText(text: String) = editor { it?.putString(TEXT_TAG, text)}
    fun getLastText() = getInstance()?.getString(TEXT_TAG, "") ?: ""

}