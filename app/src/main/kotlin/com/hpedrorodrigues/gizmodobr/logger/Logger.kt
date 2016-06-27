package com.hpedrorodrigues.gizmodobr.logger

import android.util.Log

object Logger {

    private val tag = "GZMD"

    fun i(message: String) {
        Log.i(tag, message)
    }

    fun i(message: String, throwable: Throwable) {
        Log.i(tag, message, throwable)
    }

    fun e(message: String) {
        Log.i(tag, message)
    }

    fun e(message: String, throwable: Throwable) {
        Log.i(tag, message, throwable)
    }
}