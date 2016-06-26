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

package com.hpedrorodrigues.gizmodobr.constant

enum class GizmodoAnimation(val order: Int) {
    FADE(0),
    ZOOM(1),
    SLIDE_LEFT(2),
    SLIDE_RIGHT(3),
    SLIDE_UP(4),
    SLIDE_DOWN(5);

    companion object {

        @JvmStatic fun find(order: Int): GizmodoAnimation = GizmodoAnimation
                .values().filter { it.order == order } [0]
    }
}