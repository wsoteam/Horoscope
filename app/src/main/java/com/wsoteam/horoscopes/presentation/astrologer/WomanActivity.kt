package com.wsoteam.horoscopes.presentation.astrologer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.woman_activity.*

class WomanActivity : AppCompatActivity(R.layout.woman_activity) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openAstro(PreferencesProvider.getVersion()!!)
        ivNext.setOnClickListener {
            startActivity(Intent(this@WomanActivity, WomanListActivity::class.java))
        }

        ivClose.setOnClickListener {
            onBackPressed()
        }
    }
}