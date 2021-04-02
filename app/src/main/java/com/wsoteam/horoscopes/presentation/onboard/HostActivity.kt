package com.wsoteam.horoscopes.presentation.onboard

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.BirthdayFragment
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.NameFragment
import com.wsoteam.horoscopes.presentation.onboard.pager.OnboardAdapter
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.GenderFragment
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.WelcomeFragment
import kotlinx.android.synthetic.main.host_activity.*
import kotlinx.android.synthetic.main.host_activity.diOnboard

class HostActivity : AppCompatActivity(R.layout.host_activity) {

    private lateinit var fragmentsList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fillFragmentsList()
        updateUI()

    }

    private fun fillFragmentsList() {
        fragmentsList = arrayListOf()
        fragmentsList.add(WelcomeFragment())
        fragmentsList.add(BirthdayFragment())
        fragmentsList.add(NameFragment())
        fragmentsList.add(GenderFragment())
    }

    private fun updateUI() {
        var span = SpannableString(getString(R.string.privacy_onboard))
        var startIndex = getString(R.string.privacy_onboard).indexOf("Privacy")
        span.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.span_onboard)),
            startIndex,
            span.length - 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            UnderlineSpan(),
            startIndex,
            span.length - 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvPrivacy.text = span

        tvPrivacy.setOnClickListener {
        }


        vpOnboard.adapter = OnboardAdapter(supportFragmentManager, fragmentsList)
        diOnboard.setViewPager(vpOnboard)
        vpOnboard.adapter!!.registerDataSetObserver(diOnboard.dataSetObserver)
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
            }
        })
    }
}