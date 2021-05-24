package com.wsoteam.horoscopes.presentation.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.BannerFrequency
import com.wsoteam.horoscopes.utils.analytics.new.Events
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.description_fragment.*
import kotlinx.android.synthetic.main.description_fragment.adView

class DescriptionFragment : Fragment(R.layout.description_fragment){

    private var index = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        index = arguments!!.getInt(TAG_INDEX)

        tvName.text = resources.getStringArray(R.array.names_signs)[index]
        tvInterval.text = resources.getStringArray(R.array.info_signs_intervals)[index]
        tvRuler.text = resources.getStringArray(R.array.info_signs_ruler)[index]
        tvColors.text = resources.getStringArray(R.array.info_signs_colors)[index]
        tvElement.text = resources.getStringArray(R.array.info_signs_element)[index]
        tvLuckyDays.text = resources.getStringArray(R.array.info_signs_lucky_days)[index]
        tvQuality.text = resources.getStringArray(R.array.info_signs_quality)[index]
        tvLuckyDates.text = resources.getStringArray(R.array.info_signs_lucky_dates)[index]
        tvDescription.text = resources.getStringArray(R.array.info_signs_description)[index]

        ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        Events.openSign(resources.getStringArray(R.array.names_signs)[index])


        if (PreferencesProvider.isADEnabled() && BannerFrequency.needShow() && !Config.FOR_TEST) {
            adView.visibility = View.VISIBLE
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    companion object{

        private const val TAG_INDEX = "TAG_INDEX"

        fun newInstance(index : Int) : DescriptionFragment{
            var args = Bundle().apply {
                putInt(TAG_INDEX, index)
            }
            return DescriptionFragment().apply {
                arguments = args
            }
        }
    }
}