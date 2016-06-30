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

package com.hpedrorodrigues.gzmd.dagger

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.hpedrorodrigues.gzmd.BuildConfig
import com.hpedrorodrigues.gzmd.R
import io.fabric.sdk.android.Fabric

class GizmodoApplication : Application() {

    var tracker: Tracker? = null

    private val isDebug = BuildConfig.DEBUG

    companion object {

        @JvmStatic lateinit var graph: GizmodoComponent
    }

    override fun onCreate() {
        super.onCreate()

        val core = CrashlyticsCore.Builder().disabled(isDebug).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())

        graph = DaggerGizmodoComponent.builder().gizmodoModule(GizmodoModule(this)).build()

        graph.inject(this)
    }

    fun tracker(): Tracker {
        synchronized(this) {
            if (tracker == null) {

                val analytics = GoogleAnalytics.getInstance(this)
                analytics.appOptOut = isDebug

                tracker = analytics.newTracker(R.xml.global_tracker)
                tracker?.enableAdvertisingIdCollection(true)
            }

            return tracker!!
        }
    }

    fun component(): GizmodoComponent {
        return graph
    }
}