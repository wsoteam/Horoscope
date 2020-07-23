package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R

class MatchVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.match_vh, viewGroup, false
    )
) {
    fun bind(love: Int, friend: Int, career: Int) {

    }
}