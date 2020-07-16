package com.wsoteam.horoscopes.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.form.DateDialog
import com.wsoteam.horoscopes.presentation.premium.PremiumActivity
import com.wsoteam.horoscopes.utils.Preferences
import kotlinx.android.synthetic.main.form_activity.*

class FormActivity : AppCompatActivity(R.layout.form_activity) {

    var isCanGoNext = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edtBirthdate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                DateDialog().show(supportFragmentManager, "")
            }
        }
        setNoCanGoNextState()
        setGoNextListener()
    }

    private fun setGoNextListener() {
        edtName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkTextFields()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edtBirthdate.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                checkTextFields()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun checkTextFields() {
        if (!isCanGoNext){
            if (edtName.text.toString() != "" && edtBirthdate.text.toString() != ""){
                setCanGoNextState()
            }
        }
    }

    private fun setNoCanGoNextState() {
        isCanGoNext = false
        Glide.with(this).load(R.drawable.ic_inactive_next).into(ivNext)
        ivNext.setOnClickListener(null)
    }

    private fun setCanGoNextState() {
        isCanGoNext = true
        Glide.with(this).load(R.drawable.ic_active_next).into(ivNext)
        ivNext.setOnClickListener {
            Preferences.setName(edtName.text.toString())
            Preferences.setBirthday(edtBirthdate.text.toString())
            startActivity(Intent(this, PremiumActivity::class.java))
            finish()
        }
    }

    fun setDate(date: String) {
        edtBirthdate.setText(date)
    }
}