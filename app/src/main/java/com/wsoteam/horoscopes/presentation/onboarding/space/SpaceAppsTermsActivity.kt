package com.wsoteam.horoscopes.presentation.onboarding.space

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.app_terms_activity.*

class SpaceAppsTermsActivity : AppCompatActivity(R.layout.spaace_app_terms_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.showPrem("app_terms")
        ivClose.setOnClickListener {
            openNext()
        }

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, Config.ONBOARD_SUB, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

    }

    private fun openNext(){
        startActivity(Intent(this, SpacePrivacyPoliceActivity::class.java))
    }

    private fun handlInApp() {
        Analytic.makePurchase(PreferencesProvider.getVersion()!!, "form")
        Analytic.makePurchaseFromOnboard("app_terms")
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, FormActivity::class.java))
        finishAffinity()
    }


}