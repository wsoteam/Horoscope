package com.wsoteam.horoscopes.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.DateDialog
import kotlinx.android.synthetic.main.form_activity.*

class FormActivity : AppCompatActivity(R.layout.form_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edtBirthdate.setOnClickListener {
            DateDialog().show(supportFragmentManager, "")
        }
        setStartState()
    }

    private fun setStartState() {
        Glide.with(this).load(R.drawable.ic_inactive_next).into(ivNext)
        ivNext.setOnClickListener(null)
    }

    private fun setFinishState() {
        Glide.with(this).load(R.drawable.ic_active_next).into(ivNext)
        ivNext.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun setDate(date: String) {
        edtBirthdate.setText(date)
    }
}