package com.hpedrorodrigues.gizmodobr.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

fun broadcastReceiver(callback: (Context, Intent?) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        callback(context, intent)
    }
}