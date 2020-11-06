package com.wsoteam.horoscopes.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.FormActivity
import kotlinx.android.synthetic.main.finish_activity.*

class FinishActivity : AppCompatActivity(R.layout.finish_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivClose.setOnClickListener {
            openNext()
        }
    }

    private fun openNext(){
        startActivity(Intent(this, FormActivity::class.java))
    }


}