package com.wsoteam.horoscopes.presentation.main.pager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.horoscopes.MainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.TemporaryObject
import com.wsoteam.horoscopes.models.TimeInterval
import com.wsoteam.horoscopes.models.Week
import com.wsoteam.horoscopes.presentation.main.controller.HoroscopeAdapter
import com.wsoteam.horoscopes.presentation.main.controller.IGetPrem
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.NativeProvider
import com.wsoteam.horoscopes.utils.ads.NativeSpeaker
import com.wsoteam.horoscopes.utils.analytics.Analytic
import kotlinx.android.synthetic.main.page_fragment.*
import java.io.Serializable
import java.lang.Exception

class PageFragment : Fragment(R.layout.page_fragment) {

    var text = ""
    var index = -1
    lateinit var adapter: HoroscopeAdapter
    lateinit var temporaryObject: TemporaryObject

    companion object {

        val DATA_KEY = "DATA_KEY"
        val INDEX_KEY = "INDEX_KEY"

        fun newInstance(sign: TimeInterval, index: Int): PageFragment {
            var bundle = Bundle()
            bundle.putSerializable(DATA_KEY, sign)
            bundle.putInt(INDEX_KEY, index)
            var fragment = PageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = arguments!!.getInt(INDEX_KEY)
        fillTemporaryObject()
        adapter = HoroscopeAdapter(
            temporaryObject.text,
            temporaryObject.matches,
            temporaryObject.ratings,
            temporaryObject.emotionText,
            arrayListOf(),
            isLocked(),
            object : IGetPrem {
                override fun getPrem() {
                    var before = when (index) {
                        4 -> {
                            Analytic.month_premium
                        }
                        5 -> {
                            Analytic.year_premium
                        }
                        else -> {
                            Analytic.love_premium
                        }
                    }
                    PreferencesProvider.setBeforePremium(before)
                    (activity as MainActivity).openPremSection()
                }
            }, index
        )
        if (PreferencesProvider.isNeedNewTheme) {
            cvBack.visibility = View.GONE
            rvMainWhite.visibility = View.VISIBLE
            rvMainWhite.layoutManager = LinearLayoutManager(this.context)
            rvMainWhite.adapter = adapter
        } else {
            rvMain.layoutManager = LinearLayoutManager(this.context)
            rvMain.adapter = adapter
        }

        NativeProvider.observeOnNativeList(object : NativeSpeaker {
            override fun loadFin(nativeAd: ArrayList<UnifiedNativeAd>) {
                if (PreferencesProvider.isADEnabled()) {
                    adapter.insertAds(nativeAd)
                }
            }
        })

        try {
            text = temporaryObject?.text?.substring(0, 100) + "..."
        } catch (ex: Exception) {
        }
    }

    private fun fillTemporaryObject() {
        when (index) {
            in 0..5 -> fillAsTimeInterval()
            else -> fillAsEmotion()
        }
    }

    private fun fillAsEmotion() {
        var signData = arguments!!.getSerializable(DATA_KEY) as Week
        temporaryObject = TemporaryObject(signData.text, signData.matches, signData.ratings, getChoicedText(signData))
        Log.e("LOL", temporaryObject.toString())
    }

    private fun getChoicedText(signData: Week): String {
        return when(index){
            6 -> signData.love
            7 -> signData.career
            7 -> signData.money
            8 -> signData.wellness
            else -> signData.love
        }
    }

    private fun fillAsTimeInterval() {
        var signData = arguments!!.getSerializable(DATA_KEY) as TimeInterval
        temporaryObject = TemporaryObject(signData.text, signData.matches, signData.ratings, "")
    }

    private fun isLocked(): Boolean {
        //return (index == 5 || index == 4) && PreferencesProvider.isADEnabled()
        return false
    }


    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            PreferencesProvider.setLastText(text)
            Analytic.showHoro(index)
        }
    }
}