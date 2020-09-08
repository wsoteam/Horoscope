package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlin.random.Random

class HoroscopeAdapter(
    val text: String,
    val matches: List<Int>,
    val ratings: List<Int>,
    var nativeList: ArrayList<UnifiedNativeAd>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TEXT_TYPE = 0
    val AD_TYPE = 1
    val MATCH_TYPE = 2
    val MOOD_TYPE = 3

    var nativeDiff = 0
    var counter = 0

    init {
        if (nativeList.isNotEmpty()) {
            nativeDiff++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_TYPE -> TextVH(LayoutInflater.from(parent.context), parent)
            MATCH_TYPE -> MatchVH(LayoutInflater.from(parent.context), parent)
            MOOD_TYPE -> MoodVH(LayoutInflater.from(parent.context), parent)
            AD_TYPE -> NativeVH(LayoutInflater.from(parent.context), parent)
            else -> TextVH(LayoutInflater.from(parent.context), parent)
        }
    }

    override fun getItemCount(): Int {
        return when {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TEXT_TYPE -> (holder as TextVH).bind(text)
            MATCH_TYPE -> (holder as MatchVH).bind(matches[0], matches[1], matches[2])
            MOOD_TYPE -> (holder as MoodVH).bind(ratings[0], ratings[1], ratings[2], ratings[3])
            AD_TYPE -> (holder as NativeVH).bind(nativeList[Random.nextInt(3)])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (nativeList.isNotEmpty() || position == 0){
            position
        }else {
            position + 1
        }
    }

    fun insertAds(listAds: ArrayList<UnifiedNativeAd>) {
        nativeList = listAds
        notifyDataSetChanged()
    }
}