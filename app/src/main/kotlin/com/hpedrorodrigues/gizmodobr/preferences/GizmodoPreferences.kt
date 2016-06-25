package com.hpedrorodrigues.gizmodobr.preferences

import android.content.Context
import android.preference.PreferenceManager
import com.hpedrorodrigues.gizmodobr.dagger.ForApplication
import javax.inject.Inject

class GizmodoPreferences {

    private lateinit var context: Context

    @Inject
    constructor(@ForApplication context: Context) {
        this.context = context
    }

    fun getBoolean(name: String) = PreferenceManager
            .getDefaultSharedPreferences(context).getBoolean(name, false)

    fun putBoolean(name: String, value: Boolean) {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(name, value).apply()
    }
}