package com.wsoteam.horoscopes.presentation.onboard.prem

import android.content.Intent
import android.os.Bundle
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

class EnterActivity : AppCompatActivity(R.layout.enter_prem_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ivClose.setOnClickListener {
            QuestDialog().show(supportFragmentManager, "")
        }


        btnTrial.setOnClickListener {
            SubscriptionProvider.startChoiseSub(this, SubsIds.MERMAID, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
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