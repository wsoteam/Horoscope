package com.wsoteam.horoscopes.presentation.match

import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.material.tabs.TabLayout
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.models.MatchPair.MatchPair
import com.wsoteam.horoscopes.presentation.match.controller.IClick
import com.wsoteam.horoscopes.presentation.match.controller.MatchSignsAdapter
import com.wsoteam.horoscopes.presentation.match.dialogs.UnlockDialog
import com.wsoteam.horoscopes.presentation.match.pager.MatchPagerAdapter
import com.wsoteam.horoscopes.presentation.match.pager.fragments.DateMatchFragment
import com.wsoteam.horoscopes.presentation.match.pager.fragments.SignsFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.getSignIndexShuffleArray
import kotlinx.android.synthetic.main.match_fragment.*

class MatchFragment : Fragment(R.layout.match_fragment), UnlockDialog.Callbacks {

    interface Callbacks {
        fun openMatchResultFragment(matchPair: MatchPair, ownIndex: Int, matchIndex: Int)
    }

    private lateinit var imgsArray: TypedArray
    private lateinit var signsNames: Array<String>
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var pagerAdapter: MatchPagerAdapter

    private var matchIndex = -1
    private var ownIndex = -1

    private var matchPair = MatchPair()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgsArray = resources.obtainTypedArray(R.array.match_signs_imgs)
        signsNames = resources.getStringArray(R.array.match_signs_names)

        fragmentList = arrayListOf()
        fragmentList.add(SignsFragment())
        fragmentList.add(DateMatchFragment())

        btnShow.isEnabled = false
        setOwnSign()
        pagerAdapter = MatchPagerAdapter(childFragmentManager, fragmentList)
        vpMatch.adapter = pagerAdapter
        tlType.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> vpMatch.currentItem = 0
                    1 -> vpMatch.currentItem = 1
                }
            }
        })

        btnShow.setOnClickListener {
            UnlockDialog
                .newInstance(matchPair)
                .apply {
                    setTargetFragment(this@MatchFragment, -1)
                    show(this@MatchFragment.requireFragmentManager(), "")
                }
        }
    }

    fun setMatchSign(position: Int) {
        if (position == EMPTY_SIGN_INDEX) {
            tvMatchSign.text = getString(R.string.select_sign)
            tvMatchSign.setTextColor(resources.getColor(R.color.disabled_match_sign))
            ivMatchSign.setImageDrawable(resources.getDrawable(R.drawable.img_heart_match))
            btnShow.isEnabled = false
            matchIndex = -1
        } else {
            tvMatchSign.text = signsNames[position]
            tvMatchSign.setTextColor(resources.getColor(R.color.white))
            ivMatchSign.setImageResource(imgsArray.getResourceId(position, -1))
            btnShow.isEnabled = true
            matchIndex = position
        }


        matchPair.matchImgId = imgsArray.getResourceId(matchIndex, -1)
        matchPair.matchSignName = signsNames[matchIndex]
    }

    fun setOwnSign() {
        ivOwnSign.isEnabled = false
        var index = getSignIndexShuffleArray(PreferencesProvider.getBirthday()!!)
        ivOwnSign.setImageResource(imgsArray.getResourceId(index, -1))
        tvOwnSign.text = signsNames[index]
        ownIndex = index

        matchPair.ownImgId = imgsArray.getResourceId(ownIndex, -1)
        matchPair.ownSignName = signsNames[ownIndex]
    }

    override fun showAd() {
        if (AdWorker.rewardedAd != null && PreferencesProvider.isADEnabled() && !PreferencesProvider.isShowRewarded) {
            AdWorker.rewardedAd!!.show(
                requireActivity()
            ) {
                Log.e("LOL", "SHOW")
                PreferencesProvider.isShowRewarded = true
            }
        } else {
            (requireActivity() as Callbacks).openMatchResultFragment(
                matchPair,
                ownIndex,
                matchIndex
            )
        }
    }

    override fun unlockPrem() {
    }

    companion object {
        const val EMPTY_SIGN_INDEX = -1
    }
}