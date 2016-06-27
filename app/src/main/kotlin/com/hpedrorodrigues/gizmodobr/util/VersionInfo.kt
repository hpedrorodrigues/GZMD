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

package com.hpedrorodrigues.gizmodobr.util

import android.content.Context
import android.content.pm.PackageManager
import com.hpedrorodrigues.gizmodobr.logger.Logger
import javax.inject.Inject

class VersionInfo {

    lateinit var context: Context

    @Inject
    constructor(context: Context) {
        this.context = context
    }

    fun getVersionName(): String {
        var version = "-"

        try {

            version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {

            Logger.e("Error to get package ", e)
        }

        return version
    }

    fun getVersionCode(): Int {
        var version = 0

        try {

            version = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {

            Logger.e("Error to get package ", e)
        }

        return version
    }
}