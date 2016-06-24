package com.hpedrorodrigues.gizmodobr.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

class CustomScrollListener(val view: View) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = (recyclerView?.layoutManager as LinearLayoutManager)
        view.visibility = if (layoutManager.findFirstVisibleItemPosition() == 0) View.GONE else View.VISIBLE
    }
}