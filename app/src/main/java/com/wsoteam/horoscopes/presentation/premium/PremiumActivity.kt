package com.wsoteam.horoscopes.presentation.premium

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.AdProvider
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.Preferences
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import kotlinx.android.synthetic.main.dialog_date.*
import kotlinx.android.synthetic.main.premium_activity.*

class PremiumActivity : AppCompatActivity(R.layout.premium_activity) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, "id", object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        tvCancel.setOnClickListener {
            openNextScreen()
        }
        setPrice()
    }

    private fun setPrice() {
        tvPrice.text = "${getString(R.string.prem4)}\n ${getString(R.string.prem5)} ${Preferences.getPrice()}"
    }

    private fun handlInApp() {
        Preferences.setADStatus(false)
        openNextScreen()
    }

    private fun openNextScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}