package com.hpedrorodrigues.gizmodobr.util

import android.support.v7.graphics.Palette

object PaletteUtil {

    fun getSwatch(palette: Palette): Palette.Swatch {
        var swatch = palette.vibrantSwatch

        if (swatch == null) {
            swatch = palette.darkVibrantSwatch
        }

        if (swatch == null) {
            swatch = palette.lightVibrantSwatch
        }

        if (swatch == null) {
            swatch = palette.swatches[0]
        }

        return swatch!!
    }
}