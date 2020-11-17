package com.wsoteam.horoscopes.presentation.crystals.shop.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.horoscopes.presentation.crystals.shop.IListShop

class ListShopAdapter(
    val imgsIds: IntArray,
    val names: Array<String>,
    val props: Array<String>,
    val iListShop: IListShop
) : RecyclerView.Adapter<ListShopVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListShopVH {
        var inflater = LayoutInflater.from(parent.context)
        return ListShopVH(inflater, parent, iListShop)
    }

    override fun getItemCount(): Int {
        return imgsIds.size
    }

    override fun onBindViewHolder(holder: ListShopVH, position: Int) {
        holder.bind(imgsIds[position], names[position], props[position])
    }
}