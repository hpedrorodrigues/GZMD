package com.hpedrorodrigues.gizmodobr.dagger

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.hpedrorodrigues.gizmodobr.BuildConfig
import io.fabric.sdk.android.Fabric

class GizmodoApplication : Application() {

    companion object {

        @JvmStatic
        lateinit var graph: GizmodoComponent
    }

    fun component(): GizmodoComponent {
        return graph
    }

    override fun onCreate() {
        super.onCreate()

        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build());

        graph = DaggerGizmodoComponent.builder().gizmodoModule(GizmodoModule(this)).build()

        graph.inject(this)
    }
}