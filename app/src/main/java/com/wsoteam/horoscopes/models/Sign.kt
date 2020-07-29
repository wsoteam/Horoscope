package com.wsoteam.horoscopes.models

import com.google.gson.annotations.SerializedName

data class Sign (@SerializedName("today")
                 var today : Today,
                 @SerializedName("tomorrow")
                 var tomorrow : Tomorrow,
                 @SerializedName("week")
                 var week : Week,
                 @SerializedName("month")
                 var month : Month,
                 /*@SerializedName("year")
                 var year : Year,*/
                 @SerializedName("yesterday")
                 var yesterday : Yesterday) {
}