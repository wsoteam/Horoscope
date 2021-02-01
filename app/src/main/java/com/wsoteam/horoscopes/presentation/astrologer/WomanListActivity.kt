package com.wsoteam.horoscopes.presentation.astrologer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.woman_list_activity.*

class WomanListActivity : AppCompatActivity(R.layout.woman_list_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openSecondAstro(PreferencesProvider.getVersion()!!)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivClose.setOnClickListener {
            var intent = Intent(this@WomanListActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnTryNow.setOnClickListener {
            Analytic.openAstroUrl(PreferencesProvider.getVersion()!!)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.oranum.com/chat/random-expert?s=1&p=7&w=105823&t=216&c=26199411"))
            startActivity(intent)
        }

    }
}