package com.hpedrorodrigues.gizmodobr.dagger

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.hpedrorodrigues.gizmodobr.BuildConfig
import com.hpedrorodrigues.gizmodobr.R
import io.fabric.sdk.android.Fabric

class GizmodoApplication : Application() {

    var tracker: Tracker? = null

    companion object {

        @JvmStatic
        lateinit var graph: GizmodoComponent
    }

    fun component(): GizmodoComponent {
        return graph
    }

    fun tracker(): Tracker {
        synchronized(this) {
            if (tracker == null) {

                val analytics = GoogleAnalytics.getInstance(this)
                analytics.appOptOut = BuildConfig.DEBUG

                tracker = analytics.newTracker(R.xml.global_tracker)
                tracker?.enableAdvertisingIdCollection(true)
            }

            return tracker!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build());

        graph = DaggerGizmodoComponent.builder().gizmodoModule(GizmodoModule(this)).build()

        graph.inject(this)
    }
}