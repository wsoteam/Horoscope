package com.wsoteam.horoscopes.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.net.RepositoryGets
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MainVM : ViewModel() {

    private val PT_LOCALE = "pt"

    private var job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)

    private val dataLD = MutableLiveData<List<Sign>>()

    fun preLoadData(iLoadState: ILoadState) {
        L.log("preLoadData")
        if (NetState.isConnected()) {
            job = vmScope.launch {
                try {
                    CacheData.setCachedData(getData())
                }catch (ex : Exception){
                    iLoadState.throwError(ex.message!!)
                }
            }
        }else{
            iLoadState.throwError("net_lost")
        }
    }

    fun reloadData() {
        L.log("reloadData")
        if (NetState.isConnected()) {
            job = vmScope.launch {
                dataLD.value = getData()
            }
        }
    }

    fun setupCachedData() {
        L.log("setupCachedData")
        if (CacheData.getCachedData() != null) {
            dataLD.value = CacheData.getCachedData()
            CacheData.clearCache()
        } else {
            reloadData()
        }
    }

    private suspend fun getData(): List<Sign> {
        L.log("getData")
        return if (Locale.getDefault().language == PT_LOCALE){
            RepositoryGets.getAPI().getPTData().await()
        }else{
            RepositoryGets.getAPI().getData().await()
        }
    }

    fun getLD(): MutableLiveData<List<Sign>> {
        L.log("getLD")
        return dataLD
    }

}