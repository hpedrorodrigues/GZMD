package com.hpedrorodrigues.gizmodobr.dagger

import android.app.Application
import android.location.LocationManager
import javax.inject.Inject

class GizmodoApplication : Application() {

    companion object {

        @JvmStatic
        lateinit var graph: GizmodoComponent
    }

    @Inject
    lateinit var locationManager: LocationManager

    override fun onCreate() {
        super.onCreate()

        graph = DaggerGizmodoComponent.builder().gizmodoModule(GizmodoModule(this)).build()

        graph.inject(this)

        println("App: $locationManager")
    }
}