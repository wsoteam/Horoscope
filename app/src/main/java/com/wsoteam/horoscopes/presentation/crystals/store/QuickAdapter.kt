package com.jundev.weightloss.water.controller.quick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jundev.weightloss.App
import com.jundev.weightloss.R
import com.jundev.weightloss.utils.PrefWorker

class QuickAdapter(val iQuick: IQuick) : RecyclerView.Adapter<QuickVH>() {

    var imgsIndexes = arrayListOf<Int>()
    var titles = arrayListOf<String>()
    var capacities = arrayListOf<String>()

    init {
        for (i in 0..3) {
            imgsIndexes.add(PrefWorker.getQuickData(i)!!)
            titles.add(App.getContext().resources.getStringArray(R.array.water_drinks_names)[PrefWorker.getQuickData(i)!!])
            capacities.add(App.getContext().resources.getStringArray(R.array.drink_capacity_values)[PrefWorker.getCapacityIndex(i)!!])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickVH {
        val inflater = LayoutInflater.from(parent.context)
        return QuickVH(inflater, parent, iQuick)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: QuickVH, position: Int) {
        holder.bind(imgsIndexes[position], titles[position], capacities[position])
    }
}