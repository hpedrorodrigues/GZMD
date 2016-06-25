package com.hpedrorodrigues.gizmodobr.activity

import android.content.IntentFilter
import android.os.Bundle
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.BroadcastActionKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.receiver.broadcastReceiver

class SplashScreenActivity : BaseActivity() {

    private val previewLoadedReceiver = broadcastReceiver { context, intent -> finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        registerPreviewLoadedReceiver()
    }

    override fun onStop() {
        super.onStop()

        unregisterPreviewLoadedReceiver()
    }

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Splash"

    private fun registerPreviewLoadedReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadcastActionKey.PREVIEW_LOADED)

        registerReceiver(previewLoadedReceiver, intentFilter)
    }

    private fun unregisterPreviewLoadedReceiver() = unregisterReceiver(previewLoadedReceiver)
}