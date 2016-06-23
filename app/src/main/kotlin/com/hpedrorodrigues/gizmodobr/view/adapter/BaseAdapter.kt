package com.hpedrorodrigues.gizmodobr.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.hpedrorodrigues.gizmodobr.entity.BaseEntity
import com.hpedrorodrigues.gizmodobr.view.adapter.holder.BaseHolder
import java.util.*

abstract class BaseAdapter<E, H>(val context: Context) : RecyclerView.Adapter<H>() where E : BaseEntity, H : BaseHolder {

    var content = Collections.emptyList<E>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = content.size
}