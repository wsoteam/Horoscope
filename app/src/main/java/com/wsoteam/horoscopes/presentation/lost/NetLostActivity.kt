package com.wsoteam.horoscopes.presentation.lost

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.SplashActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.net_lost_activity.*

class NetLostActivity : AppCompatActivity(R.layout.net_lost_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var attempt = PreferencesProvider.netLostAttempt
        attempt++
        PreferencesProvider.netLostAttempt = attempt
        Analytic.setNetLostAttempt(attempt)

        btnTryAgain.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            finishAffinity()
        }
    }
}