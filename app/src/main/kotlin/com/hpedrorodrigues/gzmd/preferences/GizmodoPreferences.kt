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

package com.hpedrorodrigues.gzmd.preferences

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class GizmodoPreferences {

    private lateinit var context: Context

    @Inject
    constructor(context: Context) {
        this.context = context
    }

    private fun preferences() = PreferenceManager.getDefaultSharedPreferences(context)

    fun getInt(name: String, default: Int) = PreferenceManager
            .getDefaultSharedPreferences(context).getInt(name, default)

    fun getInt(name: String) = getInt(name, -1)

    fun putInt(name: String, value: Int) = preferences().edit().putInt(name, value).apply()

    fun getLong(name: String) = getLong(name, -1L)

    fun getLong(name: String, default: Long) = PreferenceManager
            .getDefaultSharedPreferences(context).getLong(name, default)

    fun putLong(name: String, value: Long) = preferences().edit().putLong(name, value).apply()

    fun getBoolean(name: String) = PreferenceManager
            .getDefaultSharedPreferences(context).getBoolean(name, false)

    fun putBoolean(name: String, value: Boolean) = preferences().edit().putBoolean(name, value).apply()
}