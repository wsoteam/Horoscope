package com.wsoteam.horoscopes.presentation.horoscope.pager.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.today_fragment.*

class TodayFragment : Fragment(R.layout.today_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*tvGeneral.text = arguments!!.getString(TAG_GENERAL)!!
        tvLove.text = arguments!!.getString(TAG_LOVE)!!
        tvCareer.text = arguments!!.getString(TAG_CAREER)!!
        tvHealth.text = arguments!!.getString(TAG_HEALTH)!!
        tvMoney.text = arguments!!.getString(TAG_MONEY)!!*/
    }

    companion object {

        const val TAG_GENERAL = "TAG_GENERAL"
        const val TAG_LOVE = "TAG_LOVE"
        const val TAG_CAREER = "TAG_CAREER"
        const val TAG_HEALTH = "TAG_HEALTH"
        const val TAG_MONEY = "TAG_MONEY"

        fun newInstance(
            general: String,
            love: String,
            career: String,
            health: String,
            money: String
        ): TodayFragment {
            var args = Bundle().apply {
                putString(TAG_GENERAL, general)
                putString(TAG_LOVE, love)
                putString(TAG_CAREER, career)
                putString(TAG_HEALTH, health)
                putString(TAG_MONEY, money)
            }
            return TodayFragment().apply {
                arguments = args
            }
        }
    }
}