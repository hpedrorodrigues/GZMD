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

package com.hpedrorodrigues.gzmd.activity.view

import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

interface SettingsView : BaseView {

    fun closeApp(): RelativeLayout

    fun enableAutoScroll(): RelativeLayout

    fun keepScreenOn(): RelativeLayout

    fun toggleCloseTheApp(): Switch

    fun toggleEnableAutoScroll(): Switch

    fun toggleKeepScreenOn(): Switch

//    fun aboutTheApp(): LinearLayout

    fun rateTheApp(): LinearLayout

    fun shareTheApp(): LinearLayout

    fun reportABug(): LinearLayout

    fun ideaToImprove(): LinearLayout

    fun sendUsYourFeedback(): LinearLayout

    fun contactUs(): LinearLayout

    fun openSourceLicenses(): LinearLayout

    fun scrollSpeed(): DiscreteSeekBar

    fun textSize(): DiscreteSeekBar

    fun startAboutActivity()
}