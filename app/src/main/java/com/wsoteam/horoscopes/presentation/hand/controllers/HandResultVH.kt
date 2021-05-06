package com.wsoteam.horoscopes.presentation.hand.controllers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.R
import kotlinx.android.synthetic.main.hand_result_item.view.*

class HandResultVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : RecyclerView.ViewHolder(
    layoutInflater.inflate(
        R.layout.hand_result_item, viewGroup, false
    )
) {


    fun bind(
        title: String,
        handImgId: Int,
        colorTitle: Int,
        progresShapeId: Int,
        percent: Int,
        text: String
    ) {
        itemView.ivHand.setImageResource(handImgId)
        itemView.tvTitle.text = title
        itemView.tvTitle.setTextColor(itemView.resources.getColor(colorTitle))
        itemView.tvProgress.text = "$percent%"
        itemView.tvInfo.text = text
        itemView.pbMatch.progressDrawable = itemView.resources.getDrawable(progresShapeId)
        itemView.pbMatch.progress = percent
    }


}