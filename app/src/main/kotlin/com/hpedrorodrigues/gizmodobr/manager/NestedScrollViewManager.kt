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