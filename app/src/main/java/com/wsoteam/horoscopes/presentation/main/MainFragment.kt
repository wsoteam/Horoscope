package com.wsoteam.horoscopes.presentation.main

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.main.pager.PageFragment
import com.wsoteam.horoscopes.presentation.main.pager.TabsAdapter
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.experior.Experior
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.tab_lat.*
import kotlinx.android.synthetic.main.tab_lat_month.*
import kotlinx.android.synthetic.main.tab_lat_today.*
import kotlinx.android.synthetic.main.tab_lat_tomorrow.*
import kotlinx.android.synthetic.main.tab_lat_week.*
import kotlinx.android.synthetic.main.tab_lat_year.*


class MainFragment : Fragment(R.layout.main_fragment) {

    var index = -1
    lateinit var signData: Sign
    var isFirstSet = true
    var timer: CountDownTimer? = null
    var listTabsTexts : List<TextView>? = null
    var oldTabId = 0
    var isWhiteTheme = false


    companion object {

        val INDEX_KEY = "INDEX_KEY"
        val DATA_KEY = "DATA_KEY"

        fun newInstance(index: Int, sign: Sign): MainFragment {
            var bundle = Bundle()
            bundle.putInt(INDEX_KEY, index)
            bundle.putSerializable(DATA_KEY, sign)
            var fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(PreferencesProvider.isNeedNewTheme){
            isWhiteTheme = PreferencesProvider.isNeedNewTheme
            setWhiteTheme()
        }
        index = arguments!!.getInt(INDEX_KEY)
        signData = arguments!!.getSerializable(DATA_KEY) as Sign
        ivMain.setImageResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )

        listTabsTexts = listOf(tvYesterday, tvToday, tvTomorrow, tvWeek, tvMonth, tvYear)

        vpHoroscope.adapter = TabsAdapter(childFragmentManager, getFragmentsList())
        vpHoroscope.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (isWhiteTheme) {
                    listTabsTexts!![position].typeface =
                        (ResourcesCompat.getFont(activity!!, R.font.open_sans_semibold))
                    listTabsTexts!![oldTabId].typeface =
                        (ResourcesCompat.getFont(activity!!, R.font.open_sans_regular))
                    oldTabId = position
                }
                Experior.trackHoroPage(position)
                if (isFirstSet) {
                    isFirstSet = false
                } else {
                    if (PreferencesProvider.isADEnabled()) {
                        showInterAwait()
                    }
                }
                tlTime.getTabAt(position)!!.select()
            }
        })
        tlTime.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vpHoroscope))
        vpHoroscope.setCurrentItem(1, true)

        /*tvGoTostories.setOnClickListener {
            sendStory()
        }*/
    }

    private fun setWhiteTheme() {
        tvYesterday.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvYesterday.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tvToday.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvToday.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tvTomorrow.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvTomorrow.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tvWeek.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvWeek.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tvMonth.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvMonth.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tvYear.setBackgroundColor(resources.getColor(android.R.color.transparent))
        tvYear.setTextColor(resources.getColorStateList(R.color.selector_white_text_tab_color))

        tlTime.setSelectedTabIndicatorColor(resources.getColor(R.color.white_selector_tab_layout))
        dvdTab.visibility = View.VISIBLE
    }

    private fun sendStory() {
        val uri = Uri.parse(PreferencesProvider.screenURI)

        var intent = Intent("com.instagram.share.ADD_TO_STORY")
        intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", getString(R.string.facebook_app_id))
        intent.putExtra("content_url", Uri.parse("https://play.google.com/store/apps/details?id=com.wsoteam.horoscopes"))
        intent.setDataAndType(uri, "image/png")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION


        val activity: Activity = activity!!
        activity.startActivityForResult(intent, 0)
    }

    private fun showInterAwait() {
        if (timer == null) {
            timer = object : CountDownTimer(200, 100) {
                override fun onFinish() {
                    AdWorker.showInter()
                    timer = null
                }

                override fun onTick(millisUntilFinished: Long) {
                }
            }.start()
        }
    }


    private fun getFragmentsList(): List<Fragment> {
        var list = listOf<Fragment>(
            PageFragment.newInstance(signData.yesterday, 0),
            PageFragment.newInstance(signData.today, 1),
            PageFragment.newInstance(signData.tomorrow, 2),
            PageFragment.newInstance(signData.week, 3),
            PageFragment.newInstance(signData.month, 4),
            PageFragment.newInstance(signData.year, 5),

            PageFragment.newInstance(signData.year, 6),
            PageFragment.newInstance(signData.year, 7),
            PageFragment.newInstance(signData.year, 8),
            PageFragment.newInstance(signData.year, 9)
        )
        return list
    }
}