package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.mood_vh.view.*

class MoodVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.mood_vh, viewGroup, false)) {
    fun bind(
        sex: Int,
        hustle: Int,
        vibe: Int,
        success: Int
    ) {
        itemView.rvSex.setRating(sex)
        itemView.rvHustle.setRating(hustle)
        itemView.rvVibe.setRating(vibe)
        itemView.rvSuccess.setRating(success)
    }
}