package com.lolkekteam.astrohuastro.utils.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lolkekteam.astrohuastro.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RepositoryGets {

    var api: RetrofitAPI

    fun getAPI(): RetrofitAPI {
        return api
    }

    init {
        var client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        HttpLoggingInterceptor.Level.NONE
                    )
            )
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(Config.VPN_DATA_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        api = retrofit.create(RetrofitAPI::class.java)
    }
}