package com.wsoteam.horoscopes.utils

import android.content.Context
import android.content.SharedPreferences
import com.wsoteam.horoscopes.App

object Preferences {
    private const val AD = "AD_STATUS"
    private const val PRICE_TAG = "PRICE_TAG"
    private const val DEF_PRICE = "433 RUB"

    fun getInstance(): SharedPreferences? {
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

}