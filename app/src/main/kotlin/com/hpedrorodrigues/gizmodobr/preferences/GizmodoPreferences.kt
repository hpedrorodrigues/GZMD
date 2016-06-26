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