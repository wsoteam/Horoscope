package com.lolkekteam.astrohuastro.utils

import com.lolkekteam.astrohuastro.App
import com.lolkekteam.astrohuastro.R

object PriceManager {

    fun getSubId() : String{
        return App.getInstance().resources.getStringArray(R.array.prices)[PreferencesProvider.priceIndex]
    }
}