package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import kotlinx.android.synthetic.main.premium_fragment.*

class PremiumFragment : Fragment(R.layout.premium_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(activity!!, Config.ID_PRICE, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
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
        startActivity(Intent(activity, MainActivity::class.java))
        activity!!.finish()
    }
}