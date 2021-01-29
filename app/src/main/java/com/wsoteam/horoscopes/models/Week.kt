package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Week(
    @SerializedName("text")
    override var text: String,
    @SerializedName("matches")
    override var matches: List<Int>,
    @SerializedName("ratings")
    override var ratings: List<Int>,
    @SerializedName("love")
    var love: String,
    @SerializedName("career")
    var career: String,
    @SerializedName("money")
    var money: String,
    @SerializedName("wellness")
    var wellness: String
) : Serializable, TimeInterval() {
}