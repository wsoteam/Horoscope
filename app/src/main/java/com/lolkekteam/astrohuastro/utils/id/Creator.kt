package com.lolkekteam.astrohuastro.utils.id

import com.lolkekteam.astrohuastro.utils.PreferencesProvider
import java.util.*

object Creator {

    fun getId() : String{
        var id = ""
        if (PreferencesProvider.userID == PreferencesProvider.ID_EMPTY){
            PreferencesProvider.userID = UUID.randomUUID().toString()
            id = PreferencesProvider.userID
        }else{
            id = PreferencesProvider.userID
        }
        return id
    }

}