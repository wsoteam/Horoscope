package com.wsoteam.horoscopes.presentation.onboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.onboard.pager.OnboardAdapter
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.*
import com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab.ChoiseSignFragment
import com.wsoteam.horoscopes.presentation.onboard.scan.ScanIntroActivtity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.utils.remote.ABDate
import kotlinx.android.synthetic.main.host_activity.*


class HostActivity : AppCompatActivity(R.layout.host_activity) {

    private lateinit var fragmentsList: ArrayList<Fragment>
    private var isDateFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesProvider.isNeedShowInterAfterOnboard = true
        isDateFragment = ABDate.isDateNeed()
        Events.openPageOnboard(0)
        fillFragmentsList()
        updateUI()
    }

    private fun fillFragmentsList() {
        fragmentsList = arrayListOf()
        fragmentsList.add(WelcomeFragment())

        if (isDateFragment){
            fragmentsList.add(BirthdayFragment())
        }else{
            fragmentsList.add(ChoiseSignFragment())
        }

        fragmentsList.add(NameFragment())
        fragmentsList.add(GenderFragment())
        fragmentsList.add(SignInfoFragment())
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
        vpOnboard.setPagingEnabled(false)
        vpOnboard.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Events.openPageOnboard(position)
                if (position == 0) {
                    btnStart.text = getString(R.string.start_onboard)
                } else {
                    btnStart.text = getString(R.string.next_on)
                }

                if (position == 1) {
                    btnStart.isEnabled = true
                }
                hideKeyboard()
            }
        })

        btnStart.setOnClickListener {
            openNextPage(vpOnboard.currentItem)
        }
    }

    private fun openNextPage(currentItem: Int) {
        when (currentItem) {
            0 -> vpOnboard.currentItem = currentItem + 1
            1 -> {
                if (checkBirthday()) {
                    if (isDateFragment){
                        (fragmentsList[1] as BirthdayFragment).saveData()
                    }else{
                        (fragmentsList[1] as ChoiseSignFragment).saveData()
                    }
                    vpOnboard.currentItem = currentItem + 1
                }
            }
            2 -> {
                if (checkName()) {
                    (fragmentsList[2] as NameFragment).saveData()
                    vpOnboard.currentItem = currentItem + 1
                }
            }
            3 -> {
                (fragmentsList[3] as GenderFragment).saveData()
                vpOnboard.currentItem = currentItem + 1
            }
            4 -> {
                bindFinish()
            }
        }
    }

    private fun bindFinish() {
        Log.e(
            "LOL",
            " name -- ${PreferencesProvider.getName()}, gender -- ${PreferencesProvider.userGender}, time -- ${PreferencesProvider.birthTime}, birth -- ${PreferencesProvider.getBirthday()}"
        )
        startActivity(Intent(this, ScanIntroActivtity::class.java))
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun checkName(): Boolean {
        return (fragmentsList[2] as NameFragment).checkFields()
    }

    private fun checkBirthday(): Boolean {
        if (isDateFragment){
            return (fragmentsList[1] as BirthdayFragment).checkFields()
        }else{
            return true
        }

    }

    override fun onBackPressed() {
        if (vpOnboard.currentItem > 0) {
            vpOnboard.currentItem = vpOnboard.currentItem - 1
        } else {
            super.onBackPressed()
        }
    }
}