package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.premium_fragment.*
import kotlinx.android.synthetic.main.premium_host_activity.*

class PremiumHostActivity : AppCompatActivity(R.layout.premium_host_activity) {

    var open_from = ""
    var version = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.showPrem(PreferencesProvider.getBeforePremium()!!)
        version = PreferencesProvider.getVersionIndex()
        open_from = intent.getStringExtra(Config.OPEN_PREM)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContainer, PremiumFragment())
            .commit()

        ivClose.setOnClickListener {
            if (open_from == Config.OPEN_PREM_FROM_REG) {
                openNextScreen()
            }else{
                onBackPressed()
            }
        }
    }

    private fun openNextScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onBackPressed() {
        if (open_from == Config.OPEN_PREM_FROM_REG) {
            openNextScreen()
        }else{
            super.onBackPressed()
        }

    }
}