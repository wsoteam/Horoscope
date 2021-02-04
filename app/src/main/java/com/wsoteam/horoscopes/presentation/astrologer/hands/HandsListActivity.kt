package com.wsoteam.horoscopes.presentation.astrologer.hands

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.hands_list_activity.*
import kotlinx.android.synthetic.main.hands_list_activity.btnTryNow
import kotlinx.android.synthetic.main.hands_list_activity.ivBack
import kotlinx.android.synthetic.main.hands_list_activity.ivClose

class HandsListActivity : AppCompatActivity(R.layout.hands_list_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytic.openSecondAstro(PreferencesProvider.getVersion()!!)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivClose.setOnClickListener {
            var intent = Intent(this@HandsListActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnTryNow.setOnClickListener {
            Analytic.openAstroUrl(PreferencesProvider.getVersion()!!)
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.oranum.com/en/free-reading?s=1&p=7&w=105823&t=573&c=26199411"))
            startActivity(intent)
        }
    }
}