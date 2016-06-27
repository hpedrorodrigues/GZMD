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

package com.hpedrorodrigues.gizmodobr.manager

import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import com.hpedrorodrigues.gizmodobr.listener.AppBarStateChangeListener

class NestedScrollViewManager(val nestedScrollView: NestedScrollView,
                              val appBarLayout: AppBarLayout,
                              val coordinatorLayout: CoordinatorLayout) {

    private var wasFullScrolled = false

    private val durationScroll = 50L

    var cancelAutoScroll = false

    var running = false
        private set

    var isAppBarExpanded = false

    val appBarStateChangedListener = object : AppBarStateChangeListener() {

        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            isAppBarExpanded = if (state.equals(State.COLLAPSED)) false else true
        }
    }

    private fun registerAppBarScrollListener() = appBarLayout
            .addOnOffsetChangedListener(appBarStateChangedListener)

    fun enableAutoScroll() {
        registerAppBarScrollListener()

        running = true
        nestedScrollView.isSmoothScrollingEnabled = true

        val handler = Handler()

        handler.postDelayed(object : Runnable {

            override fun run() {
                if (isAppBarExpanded) {
                    scrollWithCoordinatorLayout()
                } else {
                    scrollOnlyNestedView()
                }

                if (isFullScrolled()) {
                    wasFullScrolled = true
                }

                if (!cancelAutoScroll) {
                    handler.postDelayed(this, durationScroll)
                } else {
                    running = false
                }
            }
        }, durationScroll)
    }

    fun scrollWithCoordinatorLayout() {
        (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams)
                .behavior?.onNestedPreScroll(
                coordinatorLayout,
                appBarLayout,
                nestedScrollView,
                0,
                1,
                intArrayOf(0, 0)
        )
    }

    fun scrollOnlyNestedView() {
        val targetScroll = nestedScrollView.scrollY + 1
        nestedScrollView.scrollTo(0, targetScroll)
        nestedScrollView.isSmoothScrollingEnabled = true
        ViewCompat.setNestedScrollingEnabled(nestedScrollView, false)
        val currentScrollY = nestedScrollView.scrollY
        ViewCompat.postOnAnimationDelayed(nestedScrollView, object : Runnable {

            override fun run() {
                if (currentScrollY == nestedScrollView.scrollY) {
                    ViewCompat.setNestedScrollingEnabled(nestedScrollView, true)
                    return
                }

                ViewCompat.postOnAnimation(nestedScrollView, this)
            }
        }, durationScroll)
    }

    fun isFullScrolled(): Boolean {
        var height = 0

        if (nestedScrollView.childCount > 0) {
            val childView = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)
            height = childView.bottom + nestedScrollView.paddingBottom
        }

        height -= nestedScrollView.height

        return height.equals(nestedScrollView.scrollY)
    }
}