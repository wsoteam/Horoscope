package com.wsoteam.horoscopes.utils.fcm

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

object FCMWork {
    fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("LOL", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                Log.e("LOL", "FCM token -- ${task.result?.token}")
            })
    }
}