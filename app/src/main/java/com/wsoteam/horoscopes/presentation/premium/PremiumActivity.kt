package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
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

        ivClose.setOnClickListener {
            openNextScreen()
        }
        setPrice()
    }

    private fun setPrice() {
        tvPrice.text = "${getString(R.string.prem4)} \n ${getString(R.string.prem5)} ${PreferencesProvider.getPrice()}"
    }

    private fun handlInApp() {
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }

    private fun openNextScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}