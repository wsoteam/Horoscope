package com.wsoteam.horoscopes.presentation.hand.controllers

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HandResultAdapter(
    val listTitles: Array<String>,
    val listHandsImages: TypedArray,
    val colors: TypedArray,
    val progressShapes: TypedArray,
    val percents: IntArray,
    val texts: Array<String>
) : RecyclerView.Adapter<HandResultVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HandResultVH {
        val li = LayoutInflater.from(parent.context)
        return HandResultVH(li, parent)
    }

    override fun getItemCount(): Int {
        return listTitles.size
    }

    override fun onBindViewHolder(holder: HandResultVH, position: Int) {
        holder.bind(
            listTitles[position],
            listHandsImages.getResourceId(position, -1),
            colors.getResourceId(position, -1),
            progressShapes.getResourceId(position, -1),
            percents[position],
            texts[position]
        )
    }
}