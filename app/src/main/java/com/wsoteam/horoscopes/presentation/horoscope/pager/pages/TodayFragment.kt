package com.wsoteam.horoscopes.presentation.horoscope.pager.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Week
import kotlinx.android.synthetic.main.today_fragment.*

class TodayFragment : Fragment(R.layout.today_fragment) {

    lateinit var general : String
    lateinit var week: Week


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        general = arguments!!.getString(TAG_GENERAL)!!
        week = arguments!!.getSerializable(TAG_WEEK) as Week

        tvGeneral.text = general
        tvLove.text = week.love
        tvCareer.text = week.career
        tvHealth.text = week.wellness
        tvMoney.text = week.money
    }

    companion object {

        const val TAG_GENERAL = "TAG_GENERAL"
        const val TAG_WEEK = "TAG_WEEK"

        fun newInstance(
            general: String,
            week: Week
        ): TodayFragment {
            var args = Bundle().apply {
                putString(TAG_GENERAL, general)
                putSerializable(TAG_WEEK, week)
            }
            return TodayFragment().apply {
                arguments = args
            }
        }
    }
}