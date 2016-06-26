package com.hpedrorodrigues.gizmodobr.util

import android.graphics.Color

object ColorUtil {

    fun getDarkerColor(color: Int): Int {
        val hsv = FloatArray(3)

        Color.colorToHSV(color, hsv)

        hsv[2] *= 0.9f

        return Color.HSVToColor(hsv)
    }
}