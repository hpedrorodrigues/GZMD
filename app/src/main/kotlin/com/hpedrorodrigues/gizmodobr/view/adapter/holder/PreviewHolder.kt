package com.hpedrorodrigues.gizmodobr.view.adapter.holder

import android.view.View
import android.widget.TextView
import com.hpedrorodrigues.gizmodobr.R

class PreviewHolder(view: View) : BaseHolder(view) {

    lateinit var title: TextView

    override fun createView(view: View) {
        title = view.findViewById(R.id.title) as TextView
    }
}