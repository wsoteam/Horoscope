package com.wsoteam.horoscopes.utils.analytics

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import okhttp3copy.internal.Internal.logger


object FBAnalytic {

     fun logTrial(context: Context) {
        val params = Bundle()
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD")
        AppEventsLogger
            .newLogger(context)
            .logEvent(AppEventsConstants.EVENT_NAME_START_TRIAL, 3.6, params)
    }


    fun logAdClickEvent(context: Context, adType: String) {
        val params = Bundle()
        params.putString(AppEventsConstants.EVENT_PARAM_AD_TYPE, adType)
        AppEventsLogger
            .newLogger(context)
            .logEvent(AppEventsConstants.EVENT_NAME_AD_CLICK, params)
    }
}