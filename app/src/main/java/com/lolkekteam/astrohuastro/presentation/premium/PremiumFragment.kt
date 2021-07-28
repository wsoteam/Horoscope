package com.lolkekteam.astrohuastro.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.lolkekteam.astrohuastro.Config
import com.lolkekteam.astrohuastro.MainActivity
import com.lolkekteam.astrohuastro.R
import com.lolkekteam.astrohuastro.utils.InAppCallback
import com.lolkekteam.astrohuastro.utils.PreferencesProvider
import com.lolkekteam.astrohuastro.utils.SubscriptionProvider
import com.lolkekteam.astrohuastro.utils.analytics.Analytic
import com.lolkekteam.astrohuastro.utils.analytics.FBAnalytic
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
        Analytic.makePurchase(PreferencesProvider.getBeforePremium()!!, getPlacement())
        FBAnalytic.logTrial(activity!!)
        PreferencesProvider.setADStatus(false)
        openNextScreen()
    }

    private fun getPlacement(): String {
        return if (activity!! is MainActivity){
            Analytic.main
        }else{
            Analytic.form
        }
    }

    private fun openNextScreen(){
        startActivity(Intent(activity, PaySuccessActivity::class.java))
        activity!!.finish()
    }


}