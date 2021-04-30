package com.wsoteam.horoscopes.presentation.horoscope.pager.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.today_fragment.*

class YearlyFragment : Fragment(R.layout.yearly_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*tvGeneral.text = arguments!!.getString(TodayFragment.TAG_GENERAL)!!*/
    }


    companion object{

        const val TAG_GENERAL = "TAG_GENERAL"

        fun newInstance(general : String) : YearlyFragment{
            var args = Bundle().apply {
                putString(TAG_GENERAL, general)
            }
            return YearlyFragment().apply {
                arguments = args
            }
        }
    }
}