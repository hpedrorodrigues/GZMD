package com.hpedrorodrigues.gizmodobr.service

import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectionService {

    @Inject
    constructor()

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    fun hasConnection(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}