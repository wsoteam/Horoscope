package com.wsoteam.horoscopes.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.models.Global
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.models.Today
import com.wsoteam.horoscopes.models.Yesterday
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.net.state.NetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainVM : ViewModel() {

    private val PT_LOCALE = "pt"

    private var job = Job()
    private val vmScope = CoroutineScope(Dispatchers.Main + job)

    private val dataLD = MutableLiveData<List<Sign>>()

   fun preLoadData() {
        L.log("preLoadData")
        if (NetState.isConnected()) {
            job = vmScope.launch {
                CacheData.setCachedData(getData())
            }
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

        }
    }


    fun getData(): List<Sign> {
        // Тут получаем список знаков
        /*L.log("getData")
        return if (Locale.getDefault().language == PT_LOCALE){
            RepositoryGets.getAPI().getPTData().await()
        }else{
            RepositoryGets.getAPI().getData().await()
        }*/


        /*val file_name = "number1.json"
        val bufferReader = App.getInstance().assets.open(file_name).bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }

        return data*/

       var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Sign::class.java)
        try {
            var inputStream = App.getInstance().assets.open("number1.json")
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
        }
        return null!!
    }

    fun getLD(): MutableLiveData<List<Sign>> {
        L.log("getLD")
        return dataLD
    }

}