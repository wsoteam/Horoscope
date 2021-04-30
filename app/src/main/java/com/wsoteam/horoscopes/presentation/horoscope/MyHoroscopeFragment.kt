package com.wsoteam.horoscopes.presentation.horoscope

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.tabs.TabLayout
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.presentation.horoscope.pager.HoroscopePagerAdapter
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.MonthlyFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.TodayFragment
import com.wsoteam.horoscopes.presentation.horoscope.pager.pages.YearlyFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.my_horoscope_fragment.*

class MyHoroscopeFragment : Fragment(R.layout.my_horoscope_fragment) {

    lateinit var pagerAdapter: HoroscopePagerAdapter
    var fragmentList = arrayListOf<Fragment>()
    lateinit var sign: Sign
    var index = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign = arguments!!.getSerializable(TAG_SIGN) as Sign
        index = arguments!!.getInt(TAG_INDEX)
        updateUI()
        fillFragmentList()

        pagerAdapter = HoroscopePagerAdapter(childFragmentManager, fragmentList)
        vpHoro.adapter = pagerAdapter
        tlType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vpHoro.currentItem = tab!!.position
            }
        })
    }

    private fun updateUI() {
        var drawable = VectorDrawableCompat.create(
            resources,
            resources.obtainTypedArray(R.array.match_signs_imgs).getResourceId(index, -1), null
        ) as Drawable
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.img_sign_color))

        ivSign.setImageResource(
            resources.obtainTypedArray(R.array.sign_draws).getResourceId(index, -1)
        )
        tvSign.text = resources.getStringArray(R.array.names_signs)[index]
        tvInterval.text = resources.getStringArray(R.array.info_signs_intervals)[index]
        btnAbout.setCompoundDrawables(drawable, null, null,null)
    }

    private fun fillFragmentList() {
        fragmentList.add(TodayFragment.newInstance(sign.today.text, sign.))
        fragmentList.add(MonthlyFragment())
        fragmentList.add(YearlyFragment())
    }


    companion object {

        const val TAG_SIGN = "TAG_SIGN"
        const val TAG_INDEX = "TAG_INDEX"

        fun newInstance(sign: Sign, index: Int): MyHoroscopeFragment {
            var args = Bundle().apply {
                putSerializable(TAG_SIGN, sign)
                putInt(TAG_INDEX, index)
            }
            return MyHoroscopeFragment().apply {
                arguments = args
            }
        }
    }


}