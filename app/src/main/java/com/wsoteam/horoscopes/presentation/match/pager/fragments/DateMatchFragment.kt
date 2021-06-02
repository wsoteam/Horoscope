package com.wsoteam.horoscopes.presentation.match.pager.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.match.MatchFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.BannerFrequency
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import kotlinx.android.synthetic.main.date_match_fragment.*
import kotlinx.android.synthetic.main.date_match_fragment.adView
import kotlinx.android.synthetic.main.match_result_fragment.*

class DateMatchFragment : Fragment(R.layout.date_match_fragment) {

    var isDayCorrect: Boolean = false
        get() = edtDay.text.toString() != ""
                && edtDay.text.toString() != " "
                && edtDay.text.toString().toInt() >= 1
                && edtDay.text.toString().toInt() <= 31

    var isMonthCorrect: Boolean = false
        get() = edtMonth.text.toString() != ""
                && edtMonth.text.toString() != " "
                && edtMonth.text.toString().toInt() >= 1
                && edtMonth.text.toString().toInt() <= 12


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtDay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMatchSign()
            }
        })


        edtMonth.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setMatchSign()
            }
        })


        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow() && !Config.FOR_TEST) {
            adView.visibility = View.VISIBLE
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    private fun setMatchSign() {
        if (isDayCorrect && isMonthCorrect) {
            (parentFragment as MatchFragment).setMatchSign(getSignIndexShuffleArray("${edtDay.text}.${edtMonth.text}"))
        }else{
            (parentFragment as MatchFragment).setMatchSign(MatchFragment.EMPTY_SIGN_INDEX)
        }
    }


}