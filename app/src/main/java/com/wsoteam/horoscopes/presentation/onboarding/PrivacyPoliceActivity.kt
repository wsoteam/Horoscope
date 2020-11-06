package com.wsoteam.horoscopes.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.privacy_police_activity.*

class PrivacyPoliceActivity : AppCompatActivity(R.layout.privacy_police_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivClose.setOnClickListener {
            openNext()
        }
    }

    private fun openNext(){
        startActivity(Intent(this, FinishActivity::class.java))
    }


}