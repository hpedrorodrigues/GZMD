package com.hpedrorodrigues.gizmodobr.dagger

import android.app.Application

class GizmodoApplication : Application() {

    companion object {

        @JvmStatic
        lateinit var graph: GizmodoComponent
    }

    public fun component(): GizmodoComponent {
        return graph
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerGizmodoComponent.builder().gizmodoModule(GizmodoModule(this)).build()

        graph.inject(this)
    }
}