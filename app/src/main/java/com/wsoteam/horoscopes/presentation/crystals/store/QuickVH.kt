package com.jundev.weightloss.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jundev.weightloss.R
import kotlinx.android.synthetic.main.item_quick.view.*

class QuickVH(layoutInflater: LayoutInflater, viewGroup: ViewGroup, iQuick: IQuick)
    : RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_quick, viewGroup, false)) {

    init {
        itemView.ivSettings.setOnClickListener {
            iQuick.onSettings(adapterPosition)
        }
    }

    fun bind(imgIndex : Int, title : String, capacity : String){
        itemView.tvTitle.text = title
        itemView.tvCapacity.text = capacity
        itemView.ivHead.setImageResource(getImage(imgIndex))
    }

    private fun getImage(imgIndex: Int): Int {
        return itemView.resources.obtainTypedArray(R.array.water_drinks_imgs_color).getResourceId(imgIndex, -1)
    }
}