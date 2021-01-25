package com.wsoteam.horoscopes.presentation.astrologer.hands

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.hands_activity.*

class HandsActivity : AppCompatActivity(R.layout.hands_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivClose.setOnClickListener {
            onBackPressed()
        }

        ivNext.setOnClickListener {
            startActivity(Intent(this@HandsActivity, HandsListActivity::class.java))
        }
    }
}