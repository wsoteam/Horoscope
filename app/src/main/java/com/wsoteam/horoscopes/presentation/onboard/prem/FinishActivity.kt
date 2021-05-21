package com.wsoteam.horoscopes.presentation.onboard.prem

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.BlackMainActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.premium.SubsIds
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.view.countdown.CountDownClock
import kotlinx.android.synthetic.main.enter_prem_activity.*
import kotlinx.android.synthetic.main.finish_prem_activity.*
import java.lang.Exception
import java.text.DecimalFormat

class FinishActivity : AppCompatActivity(R.layout.finish_prem_activity) {

    var time = 900_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Events.openCountDown()
        setPrice()
        tvSkip.setOnClickListener {
            Events.closeCountDown()
            startActivity(Intent(this, BlackMainActivity::class.java))
            finishAffinity()
        }

        btnBuy.setOnClickListener {
            Events.clickTrialButtonCountDown()
            SubscriptionProvider.startChoiseSub(this, SubsIds.HAND_SCAN, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

    }

    private fun setPrice() {
        try {
            var unit = PreferencesProvider.getPriceUnit()!!
            var price = PreferencesProvider.getPriceValue()!!.toDouble() / 1_000_000
            var diff = price * 0.95

            var oldPrice = diff + price

            var formatter = DecimalFormat("#0.00")

            var span = SpannableString("${formatter.format(oldPrice)} $unit")
            span.setSpan(StrikethroughSpan(), 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            tvNewPrice.text = getString(R.string.s_month, "${formatter.format(price)} $unit")
            tvOldPrice.text = span
        } catch (ex: Exception) {
            Log.e("LOL", ex.message)
        }
    }

    private fun handlInApp() {
        Events.trialCountDown()
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, BlackMainActivity::class.java))
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        countdownClockFirst.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                Log.d("here", "Countdown first is about to finish")
            }

            override fun countdownFinished() {
                Log.d("here", "Countdown first finished")
                countdownClockFirst.startCountDown(time)
            }
        })

        countdownClockFirst.startCountDown(time)
    }
}