package com.wsoteam.horoscopes.utils.net

import com.wsoteam.horoscopes.models.Sign
import retrofit2.http.GET

interface RetrofitAPI {

    @GET()
    fun getData() : List<Sign>
}