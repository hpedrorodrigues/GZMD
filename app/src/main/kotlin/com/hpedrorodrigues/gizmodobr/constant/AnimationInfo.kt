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