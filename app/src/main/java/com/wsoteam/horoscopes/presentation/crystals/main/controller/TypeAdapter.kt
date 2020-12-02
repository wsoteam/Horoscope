package com.wsoteam.horoscopes.presentation.crystals.main.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TypeAdapter(val idsTypeImgs: ArrayList<Int>, val typeNames: ArrayList<String>) : RecyclerView.Adapter<TypeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeVH {
        val inflater = LayoutInflater.from(parent.context)
        return TypeVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        return typeNames.size
    }

    override fun onBindViewHolder(holder: TypeVH, position: Int) {
        holder.bind(idsTypeImgs[position], typeNames[position])
    }
}