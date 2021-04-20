package com.wsoteam.horoscopes.models.MatchPair

import java.io.Serializable

data class MatchPair(
    var ownImgId: Int = -1,
    var ownSignName: String = "",
    var matchImgId: Int = -1,
    var matchSignName: String = ""
) : Serializable {
}