package com.hpedrorodrigues.gizmodobr.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.view.adapter.holder.PreviewHolder
import javax.inject.Inject

class PreviewAdapter : BaseAdapter<PreviewHolder>() {

    @Inject
    lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PreviewHolder? {
        return PreviewHolder(inflater.inflate(R.layout.preview, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder?, position: Int) {
        throw UnsupportedOperationException()
    }

    override fun getItemCount(): Int {
        throw UnsupportedOperationException()
    }
}