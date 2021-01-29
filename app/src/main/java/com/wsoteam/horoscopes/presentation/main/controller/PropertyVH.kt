package com.wsoteam.horoscopes.presentation.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.property_vh.view.*

class PropertyVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(layoutInflater.inflate(
    R.layout.property_vh, viewGroup, false)) {

    fun bind(text : String, imgId : Int, title : String){
        Glide.with(itemView).load(imgId).into(itemView.ivTitle)
        itemView.tvTitle.text = title
        itemView.tvText.text = text
    }
}