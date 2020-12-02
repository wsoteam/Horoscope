package com.wsoteam.horoscopes.presentation.crystals.premium

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.crystal_premium_activity.*
import kotlinx.android.synthetic.main.premium_fragment.*

class CrystalPremiumActivity : AppCompatActivity(R.layout.crystal_premium_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivCloseCrystalPremium.setOnClickListener {
            finish()
        }

        /*btnPayCrystal.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, Config.ID_PRICE, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }*/
    }


    /*private fun handlInApp() {
        Analytic.makePurchase(PreferencesProvider.getBeforePremium()!!, getPlacement())
        FirebaseAnalytics.getInstance(requireContext()).logEvent("trial", null)
        FBAnalytic.logTrial(activity!!)
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }*/
}