package com.lolkekteam.astrohuastro.models

import java.io.Serializable

abstract class TimeInterval : Serializable {
    abstract var text : String
    abstract var matches : List<Int>
    abstract var ratings : List<Int>
}