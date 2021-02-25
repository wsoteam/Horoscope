package com.wsoteam.horoscopes.presentation.premium.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.premium.SubsIds
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.app_terms_activity.*

class AppsTermsActivity : AppCompatActivity(R.layout.app_terms_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.showPrem("${PreferencesProvider.getVersion()}/app_terms")
        Analytic.terms()
        ivClose.setOnClickListener {
            openNext()
        }

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, SubsIds.GIRL, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

    }

    private fun openNext(){
        startActivity(Intent(this, PrivacyPoliceActivity::class.java))
    }

    private fun handlInApp() {
        Analytic.makePurchase(PreferencesProvider.getVersion()!!, "form")
        Analytic.makePurchaseFromOnboard("${PreferencesProvider.getVersion()}/app_terms")
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, FormActivity::class.java))
        finishAffinity()
    }


}