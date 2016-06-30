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

import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.view.Window
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView

interface PostView : BaseView {

    fun textView(): TextView

    fun nestedScrollView(): NestedScrollView

    fun backgroundImage(): ImageView

    fun collapsingToolbar(): CollapsingToolbarLayout

    fun coordinatorLayout(): CoordinatorLayout

    fun shareButton(): FloatingActionButton

    fun titleView(): TextView

    fun infoView(): TextView

    fun window(): Window

    fun appBar(): AppBarLayout

    fun showProgress()

    fun hideProgress()

    fun loadScreenOn()

    fun unloadScreenOn()
}