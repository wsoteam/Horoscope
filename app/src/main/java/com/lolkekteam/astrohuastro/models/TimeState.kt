package com.lolkekteam.astrohuastro.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
abstract class TimeState (
    @SerializedName("text")
    var text : String,
    @SerializedName("matches")
    var matches : List<Int>,
    @SerializedName("ratings")
    var ratings : List<Int>)