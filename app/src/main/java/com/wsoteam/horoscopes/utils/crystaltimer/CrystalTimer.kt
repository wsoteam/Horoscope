package com.wsoteam.horoscopes.utils.crystaltimer

import android.os.CountDownTimer
import android.util.Log
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import java.util.*

object CrystalTimer {

    const val NOT_EXIST = -1
    const val EXIST = 1
    const val IS_CONSUMED = -2
    const val END_TIME = -3
    const val ALERT_TIME = 1

    private var iCrystalTimer: ICrystalTimer? = null
    private var countDownTimer: CountDownTimer? = null

    private const val MONTH = 2592000000L
    private const val DAY = 86400000L
    private const val HOUR = 3600000L
    private const val MINUTE = 60000L
    private const val SECOND = 1000L

    private var purchaseTime = -1L
    private var alertTime = -1L
    private var endTime = -1L

    fun init(crystalNumber: Int, iCrystalTimer: ICrystalTimer) {
        var inappId = App.getInstance().resources.getStringArray(R.array.sub_ids)[crystalNumber]
        if (PreferencesProvider.getInapp(inappId) == PreferencesProvider.EMPTY_INAPP) {
            iCrystalTimer.setState(NOT_EXIST)
        } else {
            if (isConsume(inappId)) {
                iCrystalTimer.setState(IS_CONSUMED)
                consumeInApp(inappId)
            } else {
                iCrystalTimer.setState(EXIST)
                this.iCrystalTimer = iCrystalTimer
                initTimer(inappId)
            }
        }
    }

    private fun initTimer(inappId: String) {
        purchaseTime = PreferencesProvider.getInapp(inappId)!!
        endTime = MONTH + purchaseTime
        alertTime = endTime - MINUTE * 30

        countDownTimer = object : CountDownTimer(1_000_000, 500) {
            override fun onFinish() {
                countDownTimer?.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (iCrystalTimer == null) {
                    clearTimer()
                }
                if (Calendar.getInstance().timeInMillis > endTime) {
                    iCrystalTimer?.setState(END_TIME)
                    consumeInApp(inappId)
                    clearTimer()
                } else {
                    iCrystalTimer?.refreshTime(getFormatTime(endTime))
                    if (Calendar.getInstance().timeInMillis > alertTime) {
                        iCrystalTimer?.setState(ALERT_TIME)
                    }
                }
            }
        }
        countDownTimer!!.start()

    }

    private fun getFormatTime(endTime: Long): String {
        var diff = endTime - Calendar.getInstance().timeInMillis
        return when (diff) {
            in SECOND..MINUTE -> {
                App.getInstance().resources.getQuantityString(
                    R.plurals.seconds,
                    (diff / DAY).toInt(),
                    (diff / DAY).toInt())
            }
            in MINUTE..HOUR -> {
                App.getInstance().resources.getQuantityString(
                    R.plurals.minutes,
                    (diff / DAY).toInt(),
                    (diff / DAY).toInt())
            }
            in HOUR..DAY -> {
                App.getInstance().resources.getQuantityString(
                    R.plurals.hours,
                    (diff / DAY).toInt(),
                    (diff / DAY).toInt())
            }
            in DAY..MONTH -> {
                App.getInstance().resources.getQuantityString(
                    R.plurals.days,
                    (diff / DAY).toInt(),
                    (diff / DAY).toInt()
                )
            }
            else -> {
                App.getInstance().resources.getQuantityString(
                    R.plurals.seconds,
                    (diff / DAY).toInt(),
                    (diff / DAY).toInt())
            }
        }
    }

    private fun clearTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    fun unObserve(){
        iCrystalTimer = null
    }

    private fun consumeInApp(inappId: String) {

    }

    private fun isConsume(inappId: String): Boolean {
        var timeDiff = Calendar.getInstance().timeInMillis - PreferencesProvider.getInapp(inappId)!!
        return timeDiff > MONTH
    }

}