package com.hpedrorodrigues.gizmodobr.view.adapter

import android.support.v7.widget.RecyclerView
import com.hpedrorodrigues.gizmodobr.entity.BaseEntity
import com.hpedrorodrigues.gizmodobr.view.adapter.holder.BaseHolder
import java.util.*

abstract class BaseAdapter<E, H> : RecyclerView.Adapter<H>() where E : BaseEntity, H : BaseHolder {

    protected val content = ArrayList<E>()

    fun clear() {
        content.clear()
    }

    fun add(list: List<E>) {
        content.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = content.size
}