package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName

data class Tomorrow(
    @SerializedName("text")
    var text : String,
    @SerializedName("matches")
    var matches : List<Int>,
    @SerializedName("ratings")
    var ratings : List<Int>) {
}