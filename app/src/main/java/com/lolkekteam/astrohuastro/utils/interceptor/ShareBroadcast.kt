package com.lolkekteam.astrohuastro.utils.interceptor

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.lolkekteam.astrohuastro.utils.analytics.Analytic

class ShareBroadcast : BroadcastReceiver()  {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.unregisterReceiver(this)
        var component = intent!!.getParcelableExtra<ComponentName>(Intent.EXTRA_CHOSEN_COMPONENT)
        Analytic.share(component.packageName)
    }
}