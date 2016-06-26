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

package com.hpedrorodrigues.gizmodobr.listener

import android.support.design.widget.AppBarLayout

abstract class AppBarStateChangeListener() : AppBarLayout.OnOffsetChangedListener {

    enum class State { EXPANDED, COLLAPSED, IDLE }

    private var currentState = State.IDLE;

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {

            if (currentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }

            currentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (currentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }

            currentState = State.COLLAPSED;
        } else {
            if (currentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }

            currentState = State.IDLE;
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}