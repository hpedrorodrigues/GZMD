/*
 * Copyright 2016 Pedro Rodrigues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hpedrorodrigues.gizmodobr.activity

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.constant.BroadcastActionKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.extension.broadcastReceiver

class SplashScreenActivity : BaseActivity() {

    private var previewLoadedReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()

        registerPreviewLoadedReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterPreviewLoadedReceiver()
    }

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Splash"

    private fun registerPreviewLoadedReceiver() {
        if (previewLoadedReceiver == null) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(BroadcastActionKey.FINISH_SPLASH_SCREEN)

            previewLoadedReceiver = broadcastReceiver { context, intent -> finish() }

            registerReceiver(previewLoadedReceiver, intentFilter)
        }
    }

    private fun unregisterPreviewLoadedReceiver() = {
        if (previewLoadedReceiver != null) {
            unregisterReceiver(previewLoadedReceiver)
            previewLoadedReceiver = null
        }
    }
}