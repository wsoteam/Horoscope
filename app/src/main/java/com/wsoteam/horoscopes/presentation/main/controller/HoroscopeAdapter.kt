package com.wsoteam.horoscopes.presentation.main.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import com.wsoteam.horoscopes.presentation.main.MainConfig
import com.wsoteam.horoscopes.utils.date.Months
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class HoroscopeAdapter(
    val text: String,
    val matches: List<Int>,
    val ratings: List<Int>,
    val emotionText: String,
    var nativeList: ArrayList<UnifiedNativeAd>,
    var isLocked: Boolean,
    var iGetPrem: IGetPrem,
    val numberPage: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TEXT_TYPE = 0
    val AD_TYPE = 1
    val MATCH_TYPE = 2
    val MOOD_TYPE = 3

    val PROPERTY_TYPE = -1

    var nativeDiff = 0
    var counter = 0
    val delimiters = "2021 - "

    init {
        if (nativeList.isNotEmpty()) {
            nativeDiff++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_TYPE -> TextVH(LayoutInflater.from(parent.context), parent, iGetPrem)
            MATCH_TYPE -> MatchVH(LayoutInflater.from(parent.context), parent)
            MOOD_TYPE -> MoodVH(LayoutInflater.from(parent.context), parent, iGetPrem)
            AD_TYPE -> NativeVH(LayoutInflater.from(parent.context), parent)
            PROPERTY_TYPE -> PropertyVH(LayoutInflater.from(parent.context), parent)
            else -> TextVH(LayoutInflater.from(parent.context), parent, iGetPrem)
        }
    }


    override fun getItemCount(): Int {
        if (numberPage > 5) {
            return 1
        } else {
            return when {
                isLocked -> {
                    1
                }
                ratings.isNotEmpty() -> {
                    4 + nativeDiff
                }
                matches.isNotEmpty() -> {
                    2 + nativeDiff
                }
                else -> {
                    1 + nativeDiff
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TEXT_TYPE -> bindTextType(
                clearText(text),
                isLocked,
                holder as TextVH
            )
            MATCH_TYPE -> (holder as MatchVH).bind(matches[0], matches[1], matches[2])
            MOOD_TYPE -> (holder as MoodVH).bind(
                ratings[0],
                ratings[1],
                ratings[2],
                ratings[3],
                false
            )
            AD_TYPE -> (holder as NativeVH).bind(nativeList[Random.nextInt(nativeList.size)])
            PROPERTY_TYPE -> (holder as PropertyVH).bind(
                clearText(emotionText),
                getImgId(numberPage - 6),
                getTitle(numberPage - 6)
            )
        }
    }

    private fun bindTextType(
        clearText: String,
        locked: Boolean,
        textVH: TextVH
    ) {
        when (numberPage) {
            MainConfig.YESTERDAY -> textVH.bind("Yesterday", getDate(numberPage), clearText, locked)
            MainConfig.TODAY -> textVH.bind("Today", getDate(numberPage), clearText, locked)
            MainConfig.TOMORROW -> textVH.bind("Tomorrow", getDate(numberPage), clearText, locked)
            MainConfig.WEEK -> textVH.bind("Week", getWeekDate(), clearText, locked)
            MainConfig.MONTH -> textVH.bind(
                "Month",
                Months.fullList[Calendar.getInstance().get(Calendar.MONTH)],
                clearText,
                locked
            )
            MainConfig.YEAR -> textVH.bind(
                "Year",
                Calendar.getInstance().get(Calendar.YEAR).toString(),
                clearText,
                locked
            )
        }

    }

    private fun getWeekDate(): String {
        var cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.clear(Calendar.MILLISECOND)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MINUTE)
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
        var firstPart =
            "${Months.list[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.DAY_OF_MONTH)}, ${cal.get(
                Calendar.YEAR
            )}"

        var lastDayOfWeekTime = cal.timeInMillis + 86_400_000L * 6
        cal.timeInMillis = lastDayOfWeekTime
        var secondPart = "${Months.list[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.DAY_OF_MONTH)}, ${cal.get(
            Calendar.YEAR
        )}"

        return "$firstPart - $secondPart"
    }


    private fun getDate(numberPage: Int): String {
        var cal = Calendar.getInstance()
        when (numberPage) {
            MainConfig.YESTERDAY -> cal.timeInMillis = cal.timeInMillis - 86_400_000L
            MainConfig.TOMORROW -> cal.timeInMillis = cal.timeInMillis + 86_400_000L
        }
        return return "${Months.list[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.DAY_OF_MONTH)}, ${cal.get(
            Calendar.YEAR
        )}"
    }


    private fun clearText(emotionText: String): String {
        Log.e("LOL", emotionText)
        var clearText = ""
        if (emotionText.substring(0, 35).contains(delimiters)) {
            var firstElement = 0
            if (emotionText.substring(0, 35).split(delimiters).size == 3) {
                firstElement = 2
            } else if (emotionText.substring(0, 35).split(delimiters).size == 2) {
                firstElement = 1
            }
            for (i in firstElement until emotionText.split(delimiters).size) {
                clearText += emotionText.split(delimiters)[i]
            }
        } else {
            clearText = emotionText
        }

        return clearText
    }

    private fun getTitle(index: Int): String {
        return App
            .getInstance()
            .resources
            .getStringArray(R.array.titles_property)[index]
    }

    private fun getImgId(number: Int): Int {
        return App
            .getInstance()
            .resources
            .obtainTypedArray(R.array.imgs_property)
            .getResourceId(number, -1)
    }


    override fun getItemViewType(position: Int): Int {
        return if (numberPage > 5) {
            PROPERTY_TYPE
        } else if (isLocked) {
            TEXT_TYPE
        } else if (nativeList.isNotEmpty() || position == 0) {
            position
        } else {
            position + 1
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }
}