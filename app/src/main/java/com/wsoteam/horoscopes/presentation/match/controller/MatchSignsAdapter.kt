package com.wsoteam.horoscopes.presentation.match.controller

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MatchSignsAdapter(val imgsList : TypedArray, val signsNames : Array<String>, val iClick: IClick) : RecyclerView.Adapter<MatchSignVH>() {

    val EMPTY_SELECTED_ITEM = -1

    var lastSelectedItem = EMPTY_SELECTED_ITEM
    var currentSelectedItem = EMPTY_SELECTED_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchSignVH {
        val inflater = LayoutInflater.from(parent.context)
        return MatchSignVH(inflater, parent, object : IClick{
            override fun onClick(position: Int) {
                iClick.onClick(position)
                selectItem(position)
            }
        })
    }

    private fun selectItem(position: Int) {
        currentSelectedItem = position
        notifyItemChanged(currentSelectedItem)
        if (lastSelectedItem != EMPTY_SELECTED_ITEM){
            notifyItemChanged(lastSelectedItem)
        }
        lastSelectedItem = currentSelectedItem
    }

    override fun getItemCount(): Int {
        return imgsList.length()
    }

    override fun onBindViewHolder(holder: MatchSignVH, position: Int) {
        holder.bind(imgsList.getResourceId(position, -1), signsNames[position], position == currentSelectedItem)
    }
}