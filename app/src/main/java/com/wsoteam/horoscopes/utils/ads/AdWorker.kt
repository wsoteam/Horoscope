package com.wsoteam.horoscopes.utils.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.wsoteam.horoscopes.Config
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.frequency.InterFrequency
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.utils.analytics.CustomTimer
import com.wsoteam.horoscopes.utils.analytics.experior.ETimer
import com.wsoteam.horoscopes.utils.loger.L
import kotlin.random.Random

object AdWorker {

    private var mInterstitialAd: InterstitialAd? = null
    var rewardedAd: RewardedAd? = null
    private var isInit = false
    var isNeedShowInter = false
    var isFailedLoad = false
    private const val MAX_QUERY = 3
    private const val MAX_QUERY_REWARD_VIDEO = 3
    private var counterFailed = 0
    private var counterRewardFailed = 0
    var adCallbacks: AdCallbacks? = null
    var isFirstLoad = false


    fun init(context: Context?) {
        InterFrequency.runSetup()
        isFirstLoad = true
        if (context == null) return
        MobileAds.initialize(context) {
            isInit = true
            mInterstitialAd = InterstitialAd(context)
            mInterstitialAd?.adUnitId =
                context.getString(com.wsoteam.horoscopes.R.string.interstitial_id)
            mInterstitialAd?.loadAd(AdRequest.Builder().build())
            if (isFirstLoad) {
                CustomTimer.startFirstInterTimer()
                ETimer.trackStart(ETimer.FIRST_LOAD_INTER)
            }

            initReward(context)
            mInterstitialAd?.adListener = object : AdListener() {

                override fun onAdClicked() {
                    super.onAdClicked()
                    FBAnalytic.logAdClickEvent(context, "interstitial")
                }

                override fun onAdClosed() {
                    adCallbacks?.onAdClosed()
                    isNeedShowInter = false
                    ETimer.trackStart(ETimer.NEXT_LOAD_INTER)
                    CustomTimer.startNextInterTimer()
                    mInterstitialAd?.loadAd(AdRequest.Builder().build())
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    if (isFirstLoad) {
                        ETimer.trackEnd(ETimer.FIRST_LOAD_INTER)
                        CustomTimer.stopFirstInterTimer()
                        isFirstLoad = false
                    } else {
                        CustomTimer.stopNextInterTimer()
                        ETimer.trackEnd(ETimer.NEXT_LOAD_INTER)
                    }
                    adCallbacks?.onAdLoaded()
                    if (isNeedShowInter) {
                        showInter()
                    } else {
                        adCallbacks?.onAdClosed()
                    }
                }

                override fun onAdFailedToLoad(p0: Int) {
                    L.log("onAdFailedToLoad")
                    if (isFirstLoad) {
                        CustomTimer.stopFirstInterTimer()
                        ETimer.trackEnd(ETimer.FIRST_LOAD_INTER)
                    } else {
                        CustomTimer.stopNextInterTimer()
                        ETimer.trackEnd(ETimer.NEXT_LOAD_INTER)
                    }
                    counterFailed++
                    if (counterFailed <= MAX_QUERY) {
                        reload()
                    } else {
                        adCallbacks?.onAdClosed()
                        isFailedLoad = true
                    }
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    Analytic.showAd()
                }
            }
        }
    }

    private fun initReward(context: Context) {
        RewardedAd.load(
            context,
            context.resources.getString(com.wsoteam.horoscopes.R.string.reward_video),
            AdRequest.Builder().build(),
            object :
                RewardedAdLoadCallback() {

                override fun onRewardedAdFailedToLoad(p0: LoadAdError?) {
                    Log.e("LOL", "fail")
                    counterRewardFailed++
                    if (counterRewardFailed <= MAX_QUERY_REWARD_VIDEO) {
                        initReward(context)
                    }
                    rewardedAd = null
                }

                override fun onAdLoaded(p0: RewardedAd) {
                    Log.e("LOL", "loaded")
                    counterRewardFailed = 0
                    rewardedAd = p0
                }


            })
    }

    private fun reload() {
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
    }

    fun checkLoad() {
        if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun showInter() {
        if (isInit && mInterstitialAd?.isLoaded == true) {
            if (needShow() && PreferencesProvider.isADEnabled() && !Config.FOR_TEST) {
                mInterstitialAd?.show()
            } else {
                adCallbacks?.onAdClosed()
            }
        } else if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    private fun needShow(): Boolean {
        return Random.nextInt(100) <= PreferencesProvider.getPercent()!!

    }

    fun showInterWithCallbacks(adCallbacks: AdCallbacks) {
        this.adCallbacks = adCallbacks
        if (isInit && mInterstitialAd?.isLoaded == true) {
            mInterstitialAd?.show()
        } else if (isFailedLoad) {
            counterFailed = 0
            isFailedLoad = false
            reload()
        }
    }

    fun unSubscribe() {
        this.adCallbacks = null
    }

    fun showInterIsNeed() {
        if (isNeedShowInter) showInter()
    }
}