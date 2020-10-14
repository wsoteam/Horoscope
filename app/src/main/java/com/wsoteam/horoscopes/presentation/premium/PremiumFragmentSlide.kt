package com.wsoteam.horoscopes.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.premium.pager.OnboardAdapter
import com.wsoteam.horoscopes.utils.InAppCallback
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.SubscriptionProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.premium_slide_fragment.*

class PremiumFragmentSlide : Fragment(R.layout.premium_slide_fragment) {

    var onboardImagesIds = listOf<Int>(
        R.drawable.ic_img_1_adv,
        R.drawable.ic_img_2_access,
        R.drawable.ic_img_3_magicball
    )
    var timer: CountDownTimer? = null
    var counter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpOnboard.adapter = OnboardAdapter(
            childFragmentManager,
            onboardImagesIds,
            resources.getStringArray(R.array.onboard_titles)
        )
        diOnboard.setViewPager(vpOnboard)
        vpOnboard.adapter?.registerDataSetObserver(diOnboard.dataSetObserver)


        btnPay.setOnClickListener { _ ->
            SubscriptionProvider.startChoiseSub(activity!!, Config.ID_PRICE, object :
                InAppCallback {
                override fun trialSucces() {
                    handlInApp()
                }
            })
        }

        tvLater.setOnClickListener {
            activity!!.onBackPressed()
        }

        setPrice()

        timer = object : CountDownTimer(12000, 4000) {
            override fun onFinish() {
                timer?.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (counter > 2) {
                    counter = 0
                }
                vpOnboard.setCurrentItem(counter, true)
                counter++
            }
        }
        timer!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun setPrice() {
        tvPrice.text =
            "${getString(R.string.prem4)} \n ${getString(R.string.prem5)} ${PreferencesProvider.getPrice()}"
    }

    private fun handlInApp() {
        Analytic.makePurchase(PreferencesProvider.getBeforePremium()!!, getPlacement())
        FirebaseAnalytics.getInstance(requireContext()).logEvent("trial", null)
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

    private fun openNextScreen() {
        startActivity(Intent(activity, PaySuccessActivity::class.java))
        activity!!.finish()
    }
}