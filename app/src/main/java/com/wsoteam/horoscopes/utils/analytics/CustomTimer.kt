package com.wsoteam.horoscopes.utils.analytics

import java.util.*

object CustomTimer {

    private const val FIRST_SPLASH = "first_splash_loading"
    private const val NEXT_SPLASH = "next_splash_loading"

    private const val FIRST_LOAD_INTER = "splash_inter_loading"
    private const val NEXT_LOAD_INTER = "next_inter_loading"

    private const val LOAD_NATIVE = "native_loading"

    private const val EMPTY = -1L

    private var firstInterStartTime = -1L
    private var nextInterStartTime = -1L

    private var firstSplashStartTime = -1L
    private var nextSplashStartTime = -1L

    private var nativeStartTime = -1L

    private const val TRY_TYPE = "try"
    private const val EMPTY_TYPE = "empty"
    private const val MINUS_TYPE = "minus"

    private const val START_PLACEMENT = "start"
    private const val END_PLACEMENT = "end"
    private const val EMPTY_PLACEMENT = "diff"

    private const val unit = " sec"


    /////////////////
    fun startFirstInterTimer() {
        try {
            firstInterStartTime = Calendar.getInstance().timeInMillis
        } catch (ex: Exception) {
            sendError(
                FIRST_LOAD_INTER,
                TRY_TYPE,
                START_PLACEMENT
            )
        }
    }

    fun stopFirstInterTimer() {
        try {
            if (firstInterStartTime != EMPTY) {
                sendEndDiffTime(
                    FIRST_LOAD_INTER,
                    Calendar.getInstance().timeInMillis - firstInterStartTime
                )
                firstInterStartTime =
                    EMPTY
            } else {
                sendError(
                    FIRST_LOAD_INTER,
                    EMPTY_TYPE,
                    END_PLACEMENT
                )
            }
        } catch (ex: Exception) {
            sendError(
                FIRST_LOAD_INTER,
                TRY_TYPE,
                END_PLACEMENT
            )
        }
    }

    /////////////////
    fun startNextInterTimer() {
        try {
            nextInterStartTime = Calendar.getInstance().timeInMillis
        } catch (ex: Exception) {
            sendError(
                NEXT_LOAD_INTER,
                TRY_TYPE,
                START_PLACEMENT
            )
        }
    }

    fun stopNextInterTimer() {
        try {
            if (nextInterStartTime != EMPTY) {
                sendEndDiffTime(
                    NEXT_LOAD_INTER,
                    Calendar.getInstance().timeInMillis - nextInterStartTime
                )
                nextInterStartTime =
                    EMPTY
            } else {
                sendError(
                    NEXT_LOAD_INTER,
                    EMPTY_TYPE,
                    END_PLACEMENT
                )
            }
        } catch (ex: Exception) {
            sendError(
                NEXT_LOAD_INTER,
                TRY_TYPE,
                END_PLACEMENT
            )
        }
    }


    /////////////////
    fun startFirstSplashTimer() {
        try {
            firstSplashStartTime = Calendar.getInstance().timeInMillis
        } catch (ex: Exception) {
            sendError(
                FIRST_SPLASH,
                TRY_TYPE,
                START_PLACEMENT
            )
        }
    }

    fun stopFirstSplashTimer() {
        try {
            if (firstSplashStartTime != EMPTY) {
                sendEndDiffTime(
                    FIRST_SPLASH,
                    Calendar.getInstance().timeInMillis - firstSplashStartTime
                )
                firstSplashStartTime =
                    EMPTY
            } else {
                sendError(
                    FIRST_SPLASH,
                    EMPTY_TYPE,
                    END_PLACEMENT
                )
            }
        } catch (ex: Exception) {
            sendError(
                FIRST_SPLASH,
                TRY_TYPE,
                END_PLACEMENT
            )
        }
    }

    /////////////////
    fun startNextSplashTimer() {
        try {
            nextSplashStartTime = Calendar.getInstance().timeInMillis
        } catch (ex: Exception) {
            sendError(
                NEXT_SPLASH,
                TRY_TYPE,
                START_PLACEMENT
            )
        }
    }

    fun stopNextSplashTimer() {
        try {
            if (nextSplashStartTime != EMPTY) {
                sendEndDiffTime(
                    NEXT_SPLASH,
                    Calendar.getInstance().timeInMillis - nextSplashStartTime
                )
                nextSplashStartTime =
                    EMPTY
            } else {
                sendError(
                    NEXT_SPLASH,
                    EMPTY_TYPE,
                    END_PLACEMENT
                )
            }
        } catch (ex: Exception) {
            sendError(
                NEXT_SPLASH,
                TRY_TYPE,
                END_PLACEMENT
            )
        }
    }

    /////////////////
    fun startNativeTimer() {
        try {
            nativeStartTime = Calendar.getInstance().timeInMillis
        } catch (ex: Exception) {
            sendError(
                LOAD_NATIVE,
                TRY_TYPE,
                START_PLACEMENT
            )
        }
    }

    fun stopNativeTimer() {
        try {
            if (nativeStartTime != EMPTY) {
                sendEndDiffTime(
                    LOAD_NATIVE,
                    Calendar.getInstance().timeInMillis - nativeStartTime
                )
                nativeStartTime =
                    EMPTY
            } else {
                sendError(
                    LOAD_NATIVE,
                    EMPTY_TYPE,
                    END_PLACEMENT
                )
            }
        } catch (ex: Exception) {
            sendError(
                LOAD_NATIVE,
                TRY_TYPE,
                END_PLACEMENT
            )
        }
    }
    /////////////////


    private fun sendEndDiffTime(placement: String, diff: Long) {
        if (diff >= 0) {
            Analytic.trackTime(placement, "${diff / 1000}$unit")
        } else {
            sendError(
                placement,
                MINUS_TYPE,
                EMPTY_PLACEMENT
            )
        }
    }

    private fun sendError(placement: String, type: String, whenPlacement: String) {
        Analytic.sendErrorTimer(placement, type, whenPlacement)
    }

}