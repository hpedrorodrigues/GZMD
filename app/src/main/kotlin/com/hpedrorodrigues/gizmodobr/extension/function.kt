package com.hpedrorodrigues.gizmodobr.extension

import android.os.Build

fun isBeforeLollipop() = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP

fun isAfterGingerbread() = Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1