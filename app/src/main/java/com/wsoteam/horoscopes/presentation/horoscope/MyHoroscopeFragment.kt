package com.wsoteam.horoscopes.presentation.horoscope

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.horoscope.pager.HoroscopePagerAdapter
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.MonthlyFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.TodayFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.YearlyFragment
import kotlinx.android.synthetic.main.my_horoscope_fragment.*

class MyHoroscopeFragment : Fragment(R.layout.my_horoscope_fragment) {

    lateinit var pagerAdapter: HoroscopePagerAdapter
    var fragmentList = arrayListOf<Fragment>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillFragmentList()

        pagerAdapter = HoroscopePagerAdapter(childFragmentManager, fragmentList)
        vpHoro.adapter = pagerAdapter
        tlType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vpHoro.currentItem = tab!!.position
            }
        })
    }

    private fun fillFragmentList() {
        fragmentList.add(TodayFragment())
        fragmentList.add(MonthlyFragment())
        fragmentList.add(YearlyFragment())
    }
}