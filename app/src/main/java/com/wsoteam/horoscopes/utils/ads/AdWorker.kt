package com.wsoteam.horoscopes.utils.ads

import android.content.Context
import androidx.core.R
import com.google.android.gms.ads.*

object AdWorker {

    private var mInterstitialAd: InterstitialAd? = null
    private var isInit = false
    var isNeedShowInter = false
    var isFailedLoad = false
    private const val MAX_QUERY = 3
    private var counterFailed = 0
    var adCallbacks: AdCallbacks? = null


    fun init(context: Context?){
        if (context == null) return
        MobileAds.initialize(context) {
            isInit = true
            mInterstitialAd = InterstitialAd(context)
            mInterstitialAd?.adUnitId = context.getString(com.wsoteam.horoscopes.R.string.interstitial_id)
            mInterstitialAd?.loadAd(AdRequest.Builder().build())
            mInterstitialAd?.adListener = object : AdListener() {
                override fun onAdClosed() {
                    adCallbacks?.onAdClosed()
                    isNeedShowInter = false
                    mInterstitialAd?.loadAd(AdRequest.Builder().build())
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adCallbacks?.onAdLoaded()
                    if (isNeedShowInter){
                        showInter()
                    }
                }

                override fun onAdFailedToLoad(p0: Int) {
                    counterFailed ++
                    if (counterFailed <= MAX_QUERY){
                        reload()
                    }else{
                        adCallbacks?.onAdClosed()
                        isFailedLoad = true
                    }
                }
            }
        }
    }

    private fun reload(){
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
    }

    fun checkLoad(){
        if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInter(){
        if (isInit && mInterstitialAd?.isLoaded == true) {
            mInterstitialAd?.show()
        } else if(isFailedLoad){
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInterWithCallbacks(adCallbacks: AdCallbacks){
        this.adCallbacks = adCallbacks
        if (isInit && mInterstitialAd?.isLoaded == true) {
            mInterstitialAd?.show()
        } else if(isFailedLoad){
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun unSubscribe(){
        this.adCallbacks = null
    }

    fun showInterIsNeed(){
        if(isNeedShowInter) showInter()
    }
}