package com.hpedrorodrigues.gizmodobr.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpedrorodrigues.gizmodobr.observable.NetworkStateObservable

class NetworkStateChangeReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NetworkStateObservable.stateConnectionChanged()
    }
}