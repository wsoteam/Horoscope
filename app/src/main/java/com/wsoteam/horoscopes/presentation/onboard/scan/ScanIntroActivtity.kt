package com.wsoteam.horoscopes.presentation.onboard.scan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.prem.EnterActivity
import kotlinx.android.synthetic.main.scan_intro_activity.*

class ScanIntroActivtity : AppCompatActivity(R.layout.scan_intro_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnScan.setOnClickListener {
            startActivity(Intent(this, HandCameraActivity::class.java))
        }

        tvSkip.setOnClickListener {
            startActivity(Intent(this, EnterActivity::class.java))
            finishAffinity()
        }
    }
}