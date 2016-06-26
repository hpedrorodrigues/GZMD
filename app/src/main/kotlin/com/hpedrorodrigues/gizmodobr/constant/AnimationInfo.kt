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

enum class AnimationInfo(val animation: GizmodoAnimation, val reverseAnimation: GizmodoAnimation) {

    FADE(GizmodoAnimation.FADE, GizmodoAnimation.FADE),
    ZOOM(GizmodoAnimation.ZOOM, GizmodoAnimation.ZOOM),
    SLIDE_LEFT(GizmodoAnimation.SLIDE_LEFT, GizmodoAnimation.SLIDE_RIGHT),
    SLIDE_RIGHT(GizmodoAnimation.SLIDE_RIGHT, GizmodoAnimation.SLIDE_LEFT),
    SLIDE_UP(GizmodoAnimation.SLIDE_UP, GizmodoAnimation.SLIDE_DOWN),
    SLIDE_DOWN(GizmodoAnimation.SLIDE_DOWN, GizmodoAnimation.SLIDE_UP);

    companion object {

        @JvmStatic fun findReverseByAnimation(animation: GizmodoAnimation) = AnimationInfo
                .values().filter { it.animation == animation } [0].reverseAnimation
    }
}