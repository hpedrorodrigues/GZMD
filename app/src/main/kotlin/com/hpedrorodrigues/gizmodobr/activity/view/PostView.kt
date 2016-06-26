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

package com.hpedrorodrigues.gizmodobr.activity.view

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.view.Window
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView

interface PostView : BaseView {

    fun webView(): WebView

    fun textView(): TextView

    fun backgroundImage(): ImageView

    fun collapsingToolbar(): CollapsingToolbarLayout

    fun shareButton(): FloatingActionButton

    fun window(): Window

    fun appBar(): AppBarLayout

    fun showProgress()

    fun hideProgress()
}