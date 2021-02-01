package com.wsoteam.horoscopes.presentation.astrologer.hands

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.hands_activity.*

class HandsActivity : AppCompatActivity(R.layout.hands_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openAstro(PreferencesProvider.getVersion()!!)

        ivClose.setOnClickListener {
            onBackPressed()
        }

        ivNext.setOnClickListener {
            startActivity(Intent(this@HandsActivity, HandsListActivity::class.java))
        }
    }
}