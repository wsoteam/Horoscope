package com.wsoteam.horoscopes.presentation.premium.onboarding.single

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.police_girl_activity.*

class PoliceGirlActivity : AppCompatActivity(R.layout.police_girl_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ivClose.setOnClickListener {
            onBackPressed()
        }
    }
}