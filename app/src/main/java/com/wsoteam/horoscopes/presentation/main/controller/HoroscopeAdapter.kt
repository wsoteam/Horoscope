package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.wsoteam.horoscopes.App
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.match_vh.view.*
import kotlin.random.Random

class HoroscopeAdapter(
    val text: String,
    val matches: List<Int>,
    val ratings: List<Int>,
    val emotionText : String,
    var nativeList: ArrayList<UnifiedNativeAd>,
    var isLocked: Boolean,
    var iGetPrem: IGetPrem,
    val numberPage: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TEXT_TYPE = 0
    val AD_TYPE = 1
    val MATCH_TYPE = 2
    val MOOD_TYPE = 3

    val PROPERTY_TYPE = 4

    var nativeDiff = 0
    var counter = 0

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
            TEXT_TYPE -> (holder as TextVH).bind(text, isLocked)
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
                emotionText,
                getImgId(numberPage - 6),
                getTitle(numberPage - 6)
            )
        }
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