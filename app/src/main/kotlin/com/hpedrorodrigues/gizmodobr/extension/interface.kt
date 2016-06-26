package com.hpedrorodrigues.gizmodobr.extension

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import com.hpedrorodrigues.gizmodobr.entity.Preview

fun broadcastReceiver(callback: (Context, Intent?) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) = callback(context, intent)
}

fun previewClick(callback: (Preview) -> Unit) = object : PreviewAdapter.OnPreviewClick {
    override fun onClick(preview: Preview) = callback(preview)
}