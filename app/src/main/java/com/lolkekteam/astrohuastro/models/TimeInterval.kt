package com.lolkekteam.astrohuastro.models

import androidx.annotation.Keep
import java.io.Serializable
@Keep
abstract class TimeInterval : Serializable {
    abstract var text : String
    abstract var matches : List<Int>
    abstract var ratings : List<Int>
}