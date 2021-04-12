package com.wsoteam.horoscopes.presentation.onboard.prem

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.premium.SubsIds
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.view.countdown.CountDownClock
import kotlinx.android.synthetic.main.finish_prem_activity.*

class FinishActivity : AppCompatActivity(R.layout.finish_prem_activity) {

    var time = 900_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        btnBuy.setOnClickListener {
            SubscriptionProvider.startChoiseSub(this, SubsIds.MERMAID, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

    }

    private fun handlInApp() {
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        countdownClockFirst.setCountdownListener(object: CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                Log.d("here","Countdown first is about to finish")
            }

            override fun countdownFinished() {
                Log.d("here", "Countdown first finished")
                countdownClockFirst.startCountDown(time)
            }
        })

        countdownClockFirst.startCountDown(time)
    }
}