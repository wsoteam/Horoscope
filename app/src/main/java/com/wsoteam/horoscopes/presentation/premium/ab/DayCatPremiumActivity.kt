package com.wsoteam.horoscopes.presentation.premium.ab

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.cat_premium_activity.*

class DayCatPremiumActivity : AppCompatActivity(R.layout.cat_premium_activity) {

    private val TAG = "post_alert"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.showPrem(TAG)
        PreferencesProvider.isShowCatPremium = true
        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, Config.DAY_ALERT_SUB, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        btnSkip.setOnClickListener {
            openNextScreen()
        }

    }

    private fun handlInApp() {
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        Analytic.makePurchase(TAG, TAG)
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }

    private fun openNextScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}