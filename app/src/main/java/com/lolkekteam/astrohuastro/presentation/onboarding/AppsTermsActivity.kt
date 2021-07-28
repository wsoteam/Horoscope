package com.lolkekteam.astrohuastro.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lolkekteam.astrohuastro.R
import com.lolkekteam.astrohuastro.presentation.form.FormActivity
import com.lolkekteam.astrohuastro.utils.InAppCallback
import com.lolkekteam.astrohuastro.utils.PreferencesProvider
import com.lolkekteam.astrohuastro.utils.PriceManager
import com.lolkekteam.astrohuastro.utils.SubscriptionProvider
import com.lolkekteam.astrohuastro.utils.analytics.Analytic
import com.lolkekteam.astrohuastro.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.app_terms_activity.*

class AppsTermsActivity : AppCompatActivity(R.layout.app_terms_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivClose.setOnClickListener {
            openNext()
        }

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, PriceManager.getSubId(), object :
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
        Analytic.makePurchase("new_onboard", "new_onboard")
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, FormActivity::class.java))
        finishAffinity()
    }


}