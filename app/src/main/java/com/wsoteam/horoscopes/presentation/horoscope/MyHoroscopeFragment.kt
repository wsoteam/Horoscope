package com.wsoteam.horoscopes.presentation.horoscope

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.material.tabs.TabLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.horoscope.pager.HoroscopePagerAdapter
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.MonthlyFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.TodayFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.YearlyFragment
import com.wsoteam.horoscopes.presentation.profile.dialogs.TimeDialog
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.android.synthetic.main.my_horoscope_fragment.*

class MyHoroscopeFragment : Fragment(R.layout.my_horoscope_fragment) {

    lateinit var pagerAdapter: HoroscopePagerAdapter
    var fragmentList = arrayListOf<Fragment>()
    lateinit var sign: Sign
    var index = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign = arguments!!.getSerializable(TAG_SIGN) as Sign
        index = arguments!!.getInt(TAG_INDEX)
        updateUI()
        fillFragmentList()

        pagerAdapter = HoroscopePagerAdapter(childFragmentManager, fragmentList)
        vpHoro.adapter = pagerAdapter
        vpHoro.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tlType.getTabAt(position)!!.select()
            }
        })


        tlType.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vpHoro))

        btnAbout.setOnClickListener {
            (requireActivity() as MainPageCallbacks).openAbout()
        }

        btnMatch.setOnClickListener {
            (requireActivity() as MainPageCallbacks).openMatch()
        }

        llLock.setOnClickListener {
            //empty listener for intercept clicks and scroll
        }

        btnShowAd.setOnClickListener {
            showAd()
        }

        btnPrem.setOnClickListener {
            (requireActivity() as MainPageCallbacks).openPremFromMain()
        }

    }

    override fun onResume() {
        super.onResume()
        if (PreferencesProvider.isMultipleRewardAd == ABConfig.REWARD_NEED) {
            if (!PreferencesProvider.isShowRewardedMain && PreferencesProvider.isADEnabled()) {
                llLock.visibility = View.VISIBLE
            } else {
                llLock.visibility = View.GONE
            }
        } else {
            if ((!PreferencesProvider.isShowRewardedMain
                        && !PreferencesProvider.isShowRewardedScan
                        && PreferencesProvider.listShowedSigns == PreferencesProvider.EMPTY_LIST)
                && PreferencesProvider.isADEnabled()
            ) {
                llLock.visibility = View.VISIBLE
            } else {
                llLock.visibility = View.GONE
            }
        }
    }

    private fun showAd() {
        if (AdWorker.rewardedAd != null) {
            AdWorker.rewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    AdWorker.loadReward()
                }

                override fun onAdShowedFullScreenContent() {
                    Events.startRewAd(Events.ad_show_main)
                    FirebaseAnalytics.getInstance(requireContext()).logEvent("reward_show", null)
                    super.onAdShowedFullScreenContent()
                }
            }
            AdWorker.rewardedAd!!.show(
                requireActivity()
            ) {
                Events.endRewAd(Events.ad_show_main)
                PreferencesProvider.isShowRewardedMain = true
                FirebaseAnalytics.getInstance(requireContext()).logEvent("reward_end", null)
            }
        } else {
            llLock.visibility = View.GONE
        }
    }

    private fun updateUI() {
        var drawable = VectorDrawableCompat.create(
            resources,
            resources.obtainTypedArray(R.array.match_signs_imgs).getResourceId(index, -1), null
        ) as Drawable
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.img_sign_color))

        ivSign.setImageResource(
            resources.obtainTypedArray(R.array.sign_draws).getResourceId(index, -1)
        )
        tvSign.text = resources.getStringArray(R.array.names_signs)[index]
        tvInterval.text = resources.getStringArray(R.array.info_signs_intervals)[index]
        btnAbout.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    private fun fillFragmentList() {
        fragmentList.add(TodayFragment.newInstance(sign.today.text, sign.week))
        fragmentList.add(MonthlyFragment.newInstance(sign.month.text))
        fragmentList.add(YearlyFragment.newInstance(sign.year.text))
    }


    companion object {

        const val TAG_SIGN = "TAG_SIGN"
        const val TAG_INDEX = "TAG_INDEX"

        fun newInstance(sign: Sign, index: Int): MyHoroscopeFragment {
            var args = Bundle().apply {
                putSerializable(TAG_SIGN, sign)
                putInt(TAG_INDEX, index)
            }
            return MyHoroscopeFragment().apply {
                arguments = args
            }
        }
    }

    interface MainPageCallbacks {
        fun openMatch()
        fun openAbout()
        fun openPremFromMain()
    }


}