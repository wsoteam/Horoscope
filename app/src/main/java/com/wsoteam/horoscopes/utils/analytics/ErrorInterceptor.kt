package com.wsoteam.horoscopes.utils.analytics

import android.os.Build
import com.amplitude.api.Amplitude
import com.wsoteam.horoscopes.utils.PreferencesProvider
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*

object ErrorInterceptor {

    private const val ERROR_TAG = "exception"
    private const val MESSAGE_TAG = "message"
    private const val META_TAG = "meta"

    private const val DEFAULT_META = "DEFAULT_META"


    fun throwError(message: String) {
        val eventProperties = JSONObject()

        try {
            eventProperties.put(MESSAGE_TAG, message)
            eventProperties.put(META_TAG, getUserId())
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }

        Amplitude.getInstance().logEvent(ERROR_TAG, eventProperties)
    }

    fun getUserId() : String{
        if (PreferencesProvider.userMetaData == PreferencesProvider.USER_META_EMPTY){
            PreferencesProvider.userMetaData = createUserId()
        }
        return PreferencesProvider.userMetaData
    }

    private fun createUserId() : String {
        return try {
            var model = Build.MODEL
            var timeStamp = Calendar.getInstance().timeInMillis.toString()
            var uuid = UUID.randomUUID().toString()

            "$model/$timeStamp/$uuid"
        }catch (ex : Exception){
            DEFAULT_META
        }
    }
}