package com.hpedrorodrigues.gizmodobr.view.activity.view

import android.support.design.widget.FloatingActionButton
import com.malinskiy.superrecyclerview.SuperRecyclerView

interface PreviewView : BaseView {

    fun recyclerView(): SuperRecyclerView

    fun fabTop(): FloatingActionButton
}