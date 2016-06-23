package com.hpedrorodrigues.gizmodobr.view.adapter

import android.support.v7.widget.RecyclerView
import com.hpedrorodrigues.gizmodobr.view.adapter.holder.BaseHolder

abstract class BaseAdapter<T> : RecyclerView.Adapter<T>() where T : BaseHolder