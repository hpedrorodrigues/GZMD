package com.hpedrorodrigues.gizmodobr.activity.view

import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.malinskiy.superrecyclerview.SuperRecyclerView

interface PreviewView : BaseView {

    fun recyclerView(): SuperRecyclerView

    fun fabTop(): FloatingActionButton

    fun appBar(): AppBarLayout

    fun sendPreviewLoadedBroadcast()

    fun onPreviewClick(preview: Preview)
}