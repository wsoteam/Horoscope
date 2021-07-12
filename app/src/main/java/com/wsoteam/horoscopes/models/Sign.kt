package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Sign (
                 var today : Today,
                 var yesterday : Yesterday,
                 var tomorrow : Tomorrow,
                 var week : Week,
                 var month : Month,
                 var year : Year) : Serializable {
}