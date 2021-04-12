package com.wsoteam.horoscopes.presentation.onboard.prem

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.prem.dialogs.QuestDialog
import com.wsoteam.horoscopes.presentation.premium.SubsIds
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.enter_prem_activity.*
import kotlinx.android.synthetic.main.green_prem_activity.*
import java.lang.Exception
import java.text.DecimalFormat

class EnterActivity : AppCompatActivity(R.layout.enter_prem_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPrice()

        ivClose.setOnClickListener {
            QuestDialog().show(supportFragmentManager, "")
        }


        btnTrial.setOnClickListener {
            SubscriptionProvider.startChoiseSub(this, SubsIds.HAND_SCAN, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }
    }

    private fun setPrice(){
        try {
            var unit = PreferencesProvider.getPriceUnit()!!
            var price = PreferencesProvider.getPriceValue()!!.toDouble() / 1_000_000
            var diff = price / 5

            var oldPrice = diff + price

            var formatter = DecimalFormat("#0.00")

            tvTrial.text = getString(R.string.after_trial_ends_449_00_week, "${formatter.format(price)} $unit")
        }catch (ex : Exception){
            Log.e("LOL", ex.message)
        }
    }


    fun openNextScreen(){
        startActivity(Intent(this, FinishActivity::class.java))
        finish()
    }

    private fun handlInApp() {
        FirebaseAnalytics.getInstance(this).logEvent("trial", null)
        FBAnalytic.logTrial(this)
        PreferencesProvider.setADStatus(false)
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}