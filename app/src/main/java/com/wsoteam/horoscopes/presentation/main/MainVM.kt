package com.wsoteam.horoscopes.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ZodiacChoiser

class MainVM : ViewModel() {

    private var signIndex = MutableLiveData<Int>()

    fun choiceSign(){
        signIndex.value = ZodiacChoiser.choiceSign(PreferencesProvider.getBirthday()!!)
    }

    fun getSignIndex() : MutableLiveData<Int>{
        return signIndex
    }
}