package com.wsoteam.horoscopes.utils.crystaltimer

import android.os.CountDownTimer
import android.util.Log
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import java.util.*

object CrystalTimer {
    /////////////////////////////////////////////////////////////////

    /*Нет такой покупки*/
    const val NOT_EXIST = -1

    /*Покупка есть*/
    const val EXIST = 1

    /*Покупка была использована, но не была отмечена*/
    const val IS_CONSUMED = -2

    /*Покупка закончилась сейчас*/
    const val END_TIME = -3

    /*Триггер подхода времени к концу, нужно показать алерт*/
    const val ALERT_TIME = 1

    /*Состояния окончания времени кристала, алерт был показан*/
    const val ENDING_STATE = 2

    /*Обычное состояние, нужно для покраса текста в белый цвет*/
    const val NOT_ENDING_STATE = 3

    /////////////////////////////////////////////////////////////////


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

    private var isShowedEndAlert = false

    private var alertTimeShow = ""

    fun init(crystalNumber: Int, iCrystalTimer: ICrystalTimer) {
        var inappId = App.getInstance().resources.getStringArray(R.array.sub_ids)[crystalNumber]
        isShowedEndAlert = PreferencesProvider.getEndAlertState(inappId)!!
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

        if (Calendar.getInstance().timeInMillis > alertTime) {
            if (isShowedEndAlert){
                iCrystalTimer?.setState(ENDING_STATE)
            }else{
                alertTimeShow = getFormatTime(endTime)
                iCrystalTimer?.setState(ALERT_TIME)
                isShowedEndAlert = true
                PreferencesProvider.setEndAlertState(inappId, true)
            }
        } else{
            iCrystalTimer?.setState(NOT_ENDING_STATE)
        }

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
                }
            }
        }
        countDownTimer!!.start()

    }

    fun getAlertTime() : String {
        return alertTimeShow
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