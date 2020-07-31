package com.wsoteam.horoscopes.presentation.main

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.main.controller.HoroscopeAdapter
import com.wsoteam.horoscopes.presentation.main.pager.PageFragment
import com.wsoteam.horoscopes.presentation.main.pager.TabsAdapter
import com.wsoteam.horoscopes.utils.ads.AdWorker
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainFragment : Fragment(R.layout.main_fragment) {

    var index = -1
    lateinit var signData: Sign
    var isFirstSet = true
    var timer: CountDownTimer? = null

    companion object {

        val INDEX_KEY = "INDEX_KEY"
        val DATA_KEY = "DATA_KEY"

        fun newInstance(index: Int, sign: Sign): MainFragment {
            var bundle = Bundle()
            bundle.putInt(INDEX_KEY, index)
            bundle.putSerializable(DATA_KEY, sign)
            var fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("LOL", "show")
        index = arguments!!.getInt(INDEX_KEY)
        signData = arguments!!.getSerializable(DATA_KEY) as Sign
        ivMain.setImageResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )

        vpHoroscope.adapter = TabsAdapter(childFragmentManager, getFragmentsList())
        vpHoroscope.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (isFirstSet) {
                    isFirstSet = false
                } else {
                    showInterAwait()
                }
                tlTime.getTabAt(position)!!.select()
            }
        })
        tlTime.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vpHoroscope))
        vpHoroscope.setCurrentItem(1, true)

    }

    private fun showInterAwait() {
        if (timer == null){
            timer = object : CountDownTimer(200, 100){
                override fun onFinish() {
                    AdWorker.showInter()
                    timer = null
                }

                override fun onTick(millisUntilFinished: Long) {
                }
            }.start()
        }
    }
    

    private fun getFragmentsList(): List<Fragment> {
        var list = listOf<Fragment>(
            PageFragment.newInstance(signData.yesterday),
            PageFragment.newInstance(signData.today),
            PageFragment.newInstance(signData.tomorrow),
            PageFragment.newInstance(signData.week),
            PageFragment.newInstance(signData.month),
            PageFragment.newInstance(signData.year)
        )
        return list
    }
}