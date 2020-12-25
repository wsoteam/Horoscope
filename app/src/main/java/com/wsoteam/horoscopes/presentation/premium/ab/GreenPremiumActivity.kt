package com.wsoteam.horoscopes.presentation.premium.ab

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.green_prem_activity.*
import java.lang.Exception
import java.text.DecimalFormat

class GreenPremiumActivity : AppCompatActivity(R.layout.green_prem_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Analytic.showPrem(PreferencesProvider.getVersion()!!)

        btnPayGreen.setOnClickListener {
            SubscriptionProvider.startChoiseSub(this, Config.CLEANER_SUB, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        btnClose.setOnClickListener {
            openNextScreen()
        }

        setPrice()
    }


    private fun handlInApp() {
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        Analytic.makePurchase(PreferencesProvider.getVersion()!!, "form")
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }

    private fun openNextScreen(){
        startActivity(Intent(this, FormActivity::class.java))
        finishAffinity()
    }

    private fun setPrice() {
        try {
            var unit = PreferencesProvider.getPriceUnit()!!
            var price = PreferencesProvider.getPriceValue()!!.toDouble() / 1_000_000
            var diff = price / 5

            var oldPrice = diff + price

            var formatter = DecimalFormat("#0.00")

            tvCurrentPrice.text = "${formatter.format(price)} $unit/month"
            tvOldPrice.text = "${formatter.format(oldPrice)} $unit/month"
        }catch (ex : Exception){
            Log.e("LOL", ex.message)
        }
        /*//var unit = PreferencesProvider.getPrice()!!.split(" ")[1]
        //var value = PreferencesProvider.getPrice()!!.split(" ")[0]
        Log.e("LOL", "${PreferencesProvider.getPrice()!!.split(" ").size} lelelel")
        tvCurrentPrice.text = "${PreferencesProvider.getPrice()}/month"
        //tvOldPrice.text = "${value + 2}} $unit/month"*/
    }
}