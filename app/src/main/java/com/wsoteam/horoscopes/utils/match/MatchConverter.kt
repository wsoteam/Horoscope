package com.wsoteam.horoscopes.utils.match

import com.wsoteam.horoscopes.utils.PreferencesProvider

object MatchConverter {

    private const val REGEX = "."

    fun isShowedAdForIndex(index: Int): Boolean {
        var matchIndices = getMatchIndices()
        var isShowedAd = false
        if (matchIndices.size == 0) {
            return isShowedAd
        }

        for (i in matchIndices.indices) {
            if (matchIndices[i] == index) {
                isShowedAd = true
            }
        }

        return isShowedAd
    }

    private fun getMatchIndices(): ArrayList<Int> {
        var listPairs = arrayListOf<Int>()

        if (PreferencesProvider.listShowedSigns == PreferencesProvider.EMPTY_LIST) {
            return listPairs
        }

        var stringList = PreferencesProvider.listShowedSigns!!.split(REGEX)
        for (i in stringList.indices) {
            if (stringList[i] != "") {
                listPairs.add(stringList[i].toInt())
            }
        }

        return listPairs
    }

    fun addNewShowedMatchIndex(newIndex: Int) {
        var oldIndices = PreferencesProvider.listShowedSigns
        if (PreferencesProvider.listShowedSigns == PreferencesProvider.EMPTY_LIST) {
            PreferencesProvider.listShowedSigns = "$newIndex.$REGEX"
        } else {
            PreferencesProvider.listShowedSigns = "$oldIndices$newIndex$REGEX"
        }

    }
}