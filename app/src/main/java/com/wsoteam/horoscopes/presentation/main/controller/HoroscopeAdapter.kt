package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter(
    val text: String,
    val matches: List<Int>,
    val ratings: List<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TEXT_TYPE = 0
    val MATCH_TYPE = 1
    val MOOD_TYPE = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TEXT_TYPE -> TextVH(LayoutInflater.from(parent.context), parent)
            MATCH_TYPE -> MatchVH(LayoutInflater.from(parent.context), parent)
            MOOD_TYPE -> MoodVH(LayoutInflater.from(parent.context), parent)
            else -> TextVH(LayoutInflater.from(parent.context), parent)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            TEXT_TYPE -> (holder as TextVH).bind(text)
            MATCH_TYPE -> (holder as MatchVH).bind(matches[0], matches[1], matches[2])
            MOOD_TYPE -> (holder as MoodVH).bind(ratings[0], ratings[1], ratings[2], ratings[3])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}