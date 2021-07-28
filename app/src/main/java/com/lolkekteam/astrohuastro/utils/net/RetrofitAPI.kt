package com.lolkekteam.astrohuastro.utils.net

import com.lolkekteam.astrohuastro.models.Sign
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("output.php")
    fun getData() : Deferred<List<Sign>>

    @GET("output.php?lang=pt")
    fun getPTData() : Deferred<List<Sign>>
}