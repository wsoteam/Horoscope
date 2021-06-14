package com.wsoteam.horoscopes.presentation.hand

import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.FullScreenContentCallback
import com.wsoteam.horoscopes.BlackMainActivity
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.hand.controllers.HandResultAdapter
import com.wsoteam.horoscopes.presentation.hand.dialogs.UnlockScanDialog
import com.wsoteam.horoscopes.presentation.onboard.scan.HandCameraFragment
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.analytics.new.Events
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.android.synthetic.main.hand_results_fragment.*

class HandResultsFragment : Fragment(R.layout.hand_results_fragment) {

    private var index = -1

    lateinit var listTitles: Array<String>
    lateinit var listHandsImages: TypedArray
    lateinit var colors: TypedArray
    lateinit var progressShapes: TypedArray
    lateinit var percents: IntArray
    lateinit var texts: Array<String>

    lateinit var adapter: HandResultAdapter

    var dialog: UnlockScanDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        index = PreferencesProvider.handInfoIndex

        //hotfix
        if (index >= 10) {
            PreferencesProvider.handInfoIndex = 9
            index = 9
        }

        listTitles = resources.getStringArray(R.array.hands_titles)
        listHandsImages = resources.obtainTypedArray(R.array.hands_imgs)
        colors = resources.obtainTypedArray(R.array.colors)
        progressShapes = resources.obtainTypedArray(R.array.progress_shapes)


        var idPercentsList =
            resources.obtainTypedArray(R.array.hand_percents).getResourceId(index, -1)
        percents = resources.getIntArray(idPercentsList)

        var idTextsList =
            resources.obtainTypedArray(R.array.hands_texts).getResourceId(index, -1)
        texts = resources.getStringArray(idTextsList)

        adapter =
            HandResultAdapter(listTitles, listHandsImages, colors, progressShapes, percents, texts)
        rvResults.adapter = adapter
        rvResults.layoutManager = LinearLayoutManager(requireContext())


        if (PreferencesProvider.isMultipleRewardAd == ABConfig.REWARD_NEED) {
            if (PreferencesProvider.isADEnabled() && !PreferencesProvider.isShowRewardedScan) {
                flLock.visibility = View.VISIBLE
            }
        } else {
            if (PreferencesProvider.isADEnabled() && (!PreferencesProvider.isShowRewardedMain
                        && !PreferencesProvider.isShowRewardedScan
                        && PreferencesProvider.listShowedSigns == PreferencesProvider.EMPTY_LIST)
            ) {
                flLock.visibility = View.VISIBLE
            }
        }

        btnShowAd.setOnClickListener {
            showAd()
        }

        btnPrem.setOnClickListener {
            (requireActivity() as HandCameraFragment.Callbacks).openPremFromHand()
        }
    }

    private fun showAd() {
        if (AdWorker.rewardedAd != null) {
            AdWorker.rewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    AdWorker.loadReward()
                }

                override fun onAdShowedFullScreenContent() {
                    Events.startRewAd(Events.ad_show_scan)
                    super.onAdShowedFullScreenContent()
                }
            }

            AdWorker.rewardedAd!!.show(
                requireActivity()
            ) {
                Events.endRewAd(Events.ad_show_scan)
                PreferencesProvider.isShowRewardedScan = true
                flLock.visibility = View.INVISIBLE
            }
        } else {
            flLock.visibility = View.INVISIBLE
        }
    }
}