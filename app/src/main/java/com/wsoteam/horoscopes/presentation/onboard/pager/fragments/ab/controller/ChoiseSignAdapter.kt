package com.wsoteam.horoscopes.presentation.onboard.pager.fragments.ab.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChoiseSignAdapter(
    val signImgs: ArrayList<Int>,
    val signNames: Array<String>
) : RecyclerView.Adapter<SignVH>() {

    private var lastSelectedItem = 0

    interface IChoiseSignCallbacks {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignVH {
        var inflater = LayoutInflater.from(parent.context)
        return SignVH(inflater, parent, object : IChoiseSignCallbacks {
            override fun onClick(position: Int) {
                selectNewItem(position)
            }
        })
    }

    private fun selectNewItem(position: Int) {
        lastSelectedItem = position
        notifyDataSetChanged()
    }

    fun getSelectedItem(): Int {
        return lastSelectedItem
    }

    override fun getItemCount(): Int {
        return signImgs.size
    }

    override fun onBindViewHolder(holder: SignVH, position: Int) {
        holder.bind(signImgs[position], signNames[position], position == lastSelectedItem)
    }
}