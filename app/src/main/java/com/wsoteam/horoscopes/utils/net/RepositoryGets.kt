package com.wsoteam.horoscopes.utils.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.wsoteam.horoscopes.BuildConfig
import com.wsoteam.horoscopes.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryGets {

    lateinit var api : RetrofitAPI

    fun getAPI() : RetrofitAPI{
        return api
    }

    init {
        var client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE))
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(Config.VPN_DATA_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}