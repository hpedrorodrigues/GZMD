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