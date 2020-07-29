package com.wsoteam.horoscopes.presentation.main

import com.wsoteam.horoscopes.models.Sign

object CacheData {
    private var signData : List<Sign>? = null

    fun setCachedData(signData : List<Sign>){
        this.signData = signData
    }

    fun clearCache(){
        signData = null
    }

    fun getCachedData() : List<Sign>?{
        return signData
    }
}