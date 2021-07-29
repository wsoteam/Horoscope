package com.wsoteam.horoscopes.utils.ads.frequency

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wsoteam.horoscopes.BuildConfig
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic

object InterFrequency {
    private var hasRequest = false
    private val NEED_LOAD = true

    fun runSetup() {
        if (!hasRequest && NEED_LOAD) {
            hasRequest = true
            requestPercent()
        }
    }

    private fun requestPercent() {
        var path = "percent_${BuildConfig.VERSION_CODE}"
        FirebaseDatabase.getInstance("https://horoscop-4ead4-default-rtdb.firebaseio.com/")
            .reference
            .child(path)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Analytic.setFrequency(100)
                    PreferencesProvider.setPercent(100)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.getValue(Int::class.java) == null) {
                        createNewDirectory(path)
                    } else {
                        val newPercent = p0.getValue(Int::class.java) ?: 100
                        Analytic.setFrequency(newPercent)
                        PreferencesProvider.setPercent(newPercent)
                    }
                }
            })
    }

    private fun createNewDirectory(path: String) {
        FirebaseDatabase
            .getInstance("https://horoscop-4ead4-default-rtdb.firebaseio.com/")
            .reference
            .child(path)
            .setValue(100)
            .addOnSuccessListener {
                requestPercent()
            }
    }
}