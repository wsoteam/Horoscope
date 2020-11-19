package com.wsoteam.horoscopes.presentation.crystals.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.item_type.view.*

class TypeVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.item_type, viewGroup, false
    )
) {
    fun bind(imgId: Int, name: String) {
        itemView.tvTypeName.text = name
        itemView.ivType.setImageResource(imgId)
    }
}