package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.premium_fragment.*

class PremiumHostActivity : AppCompatActivity() {


    /*var layouts = listOf(R.layout.premium_fragment, R.layout.premium_fragment_sign, R.layout.premium_fragment_lock)
    var tvsPrices = listOf(R.id.tvPrice, R.id.tvPriceSign, R.id.tvPriceLock)
    var buttons = listOf(R.id.btnPay, R.id.btnPaySign, R.id.btnPayLock)

    var open_from = ""
    var version = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        version = PreferencesProvider.getVersionIndex()
        setContentView(layouts[version])
        open_from = intent.getStringExtra(Config.OPEN_PREM)

        findViewById<Button>(buttons[version]).setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(this, Config.ID_PRICE, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        *//*ivClose.setOnClickListener {
            if (open_from == Config.OPEN_PREM_FROM_REG) {
                openNextScreen()
            }else{
                onBackPressed()
            }
        }*//*
        setPrice()
    }

    private fun setPrice() {
        var price =
            "${getString(R.string.prem4)} \n ${getString(R.string.prem5)} ${PreferencesProvider.getPrice()}"
        findViewById<TextView>(tvsPrices[version]).text = price
    }

    private fun handlInApp() {
        Analytic.makePurchase()
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }

    private fun openNextScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onBackPressed() {
        if (open_from == Config.OPEN_PREM_FROM_REG) {
            openNextScreen()
        }else{
            super.onBackPressed()
        }

    }*/
}