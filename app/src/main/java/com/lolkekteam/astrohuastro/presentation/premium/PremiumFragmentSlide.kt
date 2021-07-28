package com.lolkekteam.astrohuastro.presentation.premium

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.lolkekteam.astrohuastro.Config
import com.lolkekteam.astrohuastro.MainActivity
import com.lolkekteam.astrohuastro.R
import com.lolkekteam.astrohuastro.presentation.premium.pager.OnboardAdapter
import com.lolkekteam.astrohuastro.utils.InAppCallback
import com.lolkekteam.astrohuastro.utils.PreferencesProvider
import com.lolkekteam.astrohuastro.utils.SubscriptionProvider
import com.lolkekteam.astrohuastro.utils.analytics.Analytic
import com.lolkekteam.astrohuastro.utils.analytics.FBAnalytic
import kotlinx.android.synthetic.main.premium_slide_fragment.*

class PremiumFragmentSlide : Fragment(R.layout.premium_slide_fragment) {

    private var onboardImagesIds = listOf<Int>(
        R.drawable.ic_img_1_adv,
        R.drawable.ic_img_2_access,
        R.drawable.ic_img_3_magicball
    )
    var timer: CountDownTimer? = null
    var counter = 0
    var isHand = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpOnboard.adapter = OnboardAdapter(
            childFragmentManager,
            onboardImagesIds,
            resources.getStringArray(R.array.onboard_titles)
        )
        diOnboard.setViewPager(vpOnboard)
        vpOnboard.adapter?.registerDataSetObserver(diOnboard.dataSetObserver)
        vpOnboard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
               if (isHand){
                   timer?.cancel()
                   counter = position
                   timer?.start()
               }
            }
        })


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

        timer = object : CountDownTimer(15000, 5000) {
            override fun onFinish() {
                timer?.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (counter > 2) {
                    counter = 0
                }
                isHand = false
                vpOnboard.setCurrentItem(counter, true)
                counter++
                isHand = true
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