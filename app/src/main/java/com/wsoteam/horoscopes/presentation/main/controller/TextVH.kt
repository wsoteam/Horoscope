package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.text_vh.view.*

class TextVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(
    R.layout.text_vh, viewGroup, false)) {

    fun bind(text: String) {
        itemView.tvText.text = text

    }
}