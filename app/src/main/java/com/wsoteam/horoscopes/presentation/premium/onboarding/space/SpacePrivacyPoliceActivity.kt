package com.wsoteam.horoscopes.presentation.premium.onboarding.space

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
import kotlinx.android.synthetic.main.space_privacy_police_activity.*

class SpacePrivacyPoliceActivity : AppCompatActivity(R.layout.space_privacy_police_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.showPrem("${PreferencesProvider.getVersion()}/privacy")
        Analytic.privacy()
        ivClose.setOnClickListener {
            openNext()
        }

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, SubsIds.SPACE, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }
    }

    private fun openNext(){
        startActivity(Intent(this, SpaceFinishActivity::class.java))
    }

    private fun handlInApp() {
        Analytic.makePurchase(PreferencesProvider.getVersion()!!, "form")
        Analytic.makePurchaseFromOnboard("${PreferencesProvider.getVersion()}/privacy")
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, FormActivity::class.java))
        finishAffinity()
    }


}