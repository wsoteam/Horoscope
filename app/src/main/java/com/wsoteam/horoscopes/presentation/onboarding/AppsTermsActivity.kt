package com.wsoteam.horoscopes.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.app_terms_activity.*

class AppsTermsActivity : AppCompatActivity(R.layout.app_terms_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivClose.setOnClickListener {
            openNext()
        }
    }

    private fun openNext(){
        startActivity(Intent(this, SubscriptionTermsActivity::class.java))
    }
}