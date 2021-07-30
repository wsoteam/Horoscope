package com.lolkekteam.astrohuastro.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Keep
data class Sign (@SerializedName("today")
                 var today : Today,
                 @SerializedName("tomorrow")
                 var tomorrow : Tomorrow,
                 @SerializedName("week")
                 var week : Week,
                 @SerializedName("month")
                 var month : Month,
                 @SerializedName("year")
                 var year : Year,
                 @SerializedName("yesterday")
                 var yesterday : Yesterday) : Serializable {
}