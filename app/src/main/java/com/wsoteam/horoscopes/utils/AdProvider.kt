package com.wsoteam.horoscopes.utils

import android.content.Context
import com.google.android.gms.ads.*
import com.wsoteam.horoscopes.R

object AdProvider {

    private var mInterstitialAd: InterstitialAd? = null
    private var counterFailed = 0
    private const val MAX_QUERY = 3
    var isFailedLoad = false


    fun init(context: Context?) {
        if (context == null) return
        MobileAds.initialize(context)
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd?.adUnitId = context.resources.getString(R.string.interstitial_id)
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(p0: Int) {
                counterFailed++
                if (counterFailed <= MAX_QUERY) {
                    reload()
                } else {
                    isFailedLoad = true
                }
            }
        }
    }

    private fun reload() {
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
    }
}