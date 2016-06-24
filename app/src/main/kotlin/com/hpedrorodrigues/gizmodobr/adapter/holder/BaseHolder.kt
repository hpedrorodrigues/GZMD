package com.hpedrorodrigues.gizmodobr.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    init {
        createView(view)
    }

    protected abstract fun createView(view: View)
}