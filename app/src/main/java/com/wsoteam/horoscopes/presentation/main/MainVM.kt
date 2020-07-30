package com.wsoteam.horoscopes.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ZodiacChoiser
import com.wsoteam.horoscopes.utils.net.RepositoryGets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainVM : ViewModel() {

    private var job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)

    private val dataLD = MutableLiveData<List<Sign>>()

    fun preLoadData() {
        job = vmScope.launch {
            CacheData.setCachedData(getData())
        }
    }

    fun reloadData() {
        job = vmScope.launch {
            dataLD.value = getData()
        }
    }

    fun setupCachedData(){
        if (CacheData.getCachedData() != null){
            dataLD.value = CacheData.getCachedData()
            CacheData.clearCache()
        }else{
            reloadData()
        }
    }

    private suspend fun getData(): List<Sign> {
        return RepositoryGets.getAPI().getData().await()
    }

    fun getLD(): MutableLiveData<List<Sign>> {
        return dataLD
    }

}