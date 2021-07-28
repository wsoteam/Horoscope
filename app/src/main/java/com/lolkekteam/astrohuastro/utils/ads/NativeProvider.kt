package com.lolkekteam.astrohuastro.utils.ads

object NativeProvider {

    /*var adsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var bufferAdsList: ArrayList<UnifiedNativeAd> = arrayListOf()
    var nativeSpeaker: NativeSpeaker? = null
    var adLoader: AdLoader? = null
    val NATIVE_ITEMS_MAX = 3
    var counter = 0

    fun loadNative(){
        adLoader = AdLoader
            .Builder(App.getInstance(), App.getInstance().getString(R.string.native_ad))
            .forUnifiedNativeAd { nativeAD ->
                bufferAdsList.add(nativeAD)
                if (!adLoader!!.isLoading) {
                    endLoading()
                }
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: Int) {
                    if (!adLoader!!.isLoading) {
                        endLoading()
                    }
                }
            }).build()
        adLoader?.loadAds(AdRequest.Builder().build(), NATIVE_ITEMS_MAX)
    }

    private fun endLoading() {
        if (bufferAdsList.size > 0) {
            adsList = bufferAdsList
            bufferAdsList = arrayListOf()
            nativeSpeaker?.loadFin(adsList)
        }
    }


    fun observeOnNativeList(nativeSpeaker: NativeSpeaker) {
        if (adsList.size > 0) {
            nativeSpeaker.loadFin(adsList)
        } else {
            this.nativeSpeaker = nativeSpeaker
        }
    }

    fun refreshNativeAd(context: Context) {
        nativeSpeaker = null
        //loadNative(context)
    }*/
}