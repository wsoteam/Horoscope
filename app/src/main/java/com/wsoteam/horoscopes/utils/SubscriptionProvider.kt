package com.wsoteam.horoscopes.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.android.billingclient.api.*
import com.yandex.metrica.YandexMetrica

object SubscriptionProvider : PurchasesUpdatedListener, BillingClientStateListener {

    private lateinit var playStoreBillingClient: BillingClient
    private var inAppCallback: InAppCallback? = null



    fun init(context: Context) {
        playStoreBillingClient = BillingClient.newBuilder(context.applicationContext)
            .enablePendingPurchases() // required or app will crash
            .setListener(this).build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        if (!playStoreBillingClient.isReady) {
            playStoreBillingClient.startConnection(this)
            return true
        }
        return false
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
        if (billingResult!!.responseCode == BillingClient.BillingResponseCode.OK) {
            if (purchases != null && purchases.size > 0 && purchases[0].purchaseState == Purchase.PurchaseState.PURCHASED){
                inAppCallback?.trialSucces()
                if(!purchases[0].isAcknowledged){
                    val params = AcknowledgePurchaseParams
                        .newBuilder()
                        .setPurchaseToken(purchases[0].purchaseToken)
                        .build()
                    var listener = AcknowledgePurchaseResponseListener {
                        Log.e("LOL", "confirmed")
                    }
                    playStoreBillingClient.acknowledgePurchase(params, listener)
                }
            }

        }
    }

    override fun onBillingServiceDisconnected() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            var isADEnabled = true
            var result = playStoreBillingClient.queryPurchases(BillingClient.SkuType.SUBS)
            if (result != null && result.purchasesList.size > 0) {
                isADEnabled = false
            }
            PreferencesProvider.setADStatus(isADEnabled)
        }
    }

    fun startChoiseSub(activity: Activity, id: String, callback: InAppCallback) {
        inAppCallback = callback
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(id))
            .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList.orEmpty().isNotEmpty()) {
                        skuDetailsList.forEach {
                            val perchaseParams = BillingFlowParams.newBuilder().setSkuDetails(it)
                                .build()
                            playStoreBillingClient.launchBillingFlow(activity, perchaseParams)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    fun startGettingPrice(id: String) : String{
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(id))
            .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList.isNotEmpty()){
                        try {
                            PreferencesProvider.setPrice(skuDetailsList[0].price)
                        }catch (ex: Exception){
                            YandexMetrica.reportEvent("error price set")
                        }
                    }
                }
            }
        }
        return ""
    }


}