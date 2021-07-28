package com.lolkekteam.astrohuastro.presentation.main.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lolkekteam.astrohuastro.MainActivity
import com.lolkekteam.astrohuastro.R
import com.lolkekteam.astrohuastro.models.TimeInterval
import com.lolkekteam.astrohuastro.presentation.main.controller.HoroscopeAdapter
import com.lolkekteam.astrohuastro.presentation.main.controller.IGetPrem
import com.lolkekteam.astrohuastro.utils.PreferencesProvider
import com.lolkekteam.astrohuastro.utils.analytics.Analytic
import kotlinx.android.synthetic.main.page_fragment.*

class PageFragment : Fragment(R.layout.page_fragment) {

    var text = ""
    var index = -1
    lateinit var adapter: HoroscopeAdapter

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
        var signData = arguments!!.getSerializable(DATA_KEY) as TimeInterval
        index = arguments!!.getInt(INDEX_KEY)
        rvMain.layoutManager = LinearLayoutManager(this.context)
        adapter = HoroscopeAdapter(
            signData.text,
            signData.matches,
            signData.ratings,
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
            })
        rvMain.adapter = adapter

        text = signData.text.substring(0, 100) + "..."

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