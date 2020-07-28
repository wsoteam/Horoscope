package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import kotlinx.android.synthetic.main.premium_fragment.*

class PremiumHostActivity : AppCompatActivity(R.layout.premium_fragment) {

    var open_from = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        open_from = intent.getStringExtra(Config.OPEN_PREM)

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, "id", object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        ivClose.setOnClickListener {
            if (open_from == Config.OPEN_PREM_FROM_REG) {
                openNextScreen()
            }else{
                onBackPressed()
            }
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