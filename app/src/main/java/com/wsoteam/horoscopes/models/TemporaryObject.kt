package com.wsoteam.horoscopes.models

data class TemporaryObject(
    var text: String,
    var matches: List<Int>,
    var ratings: List<Int>,
    var emotionText: String
)
