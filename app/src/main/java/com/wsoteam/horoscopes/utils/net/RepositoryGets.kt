package com.wsoteam.horoscopes.utils.net

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.wsoteam.horoscopes.BuildConfig
import com.wsoteam.horoscopes.Config
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
        var gson = GsonBuilder()
            .setLenient()
            .create()

        var client = OkHttpClient.Builder()
            .addNetworkInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .header("User-Agent", "LOLKEK")
                        .build()
                )
            }
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
            )
            .connectTimeout(60, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.MINUTES)
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(Config.VPN_DATA_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        api = retrofit.create(RetrofitAPI::class.java)
    }
}