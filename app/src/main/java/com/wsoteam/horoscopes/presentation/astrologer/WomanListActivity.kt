package com.wsoteam.horoscopes.presentation.astrologer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.woman_list_activity.*

class WomanListActivity : AppCompatActivity(R.layout.woman_list_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivClose.setOnClickListener {
            var intent = Intent(this@WomanListActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}