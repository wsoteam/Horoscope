package com.lolkekteam.astrohuastro.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Keep
data class Today(
    @SerializedName("text")
    override var text : String,
    @SerializedName("matches")
    override var matches : List<Int>,
    @SerializedName("ratings")
    override var ratings : List<Int>) : Serializable, TimeInterval() {
}