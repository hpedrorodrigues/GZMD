package com.hpedrorodrigues.gizmodobr.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.dagger.ForApplication
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.view.adapter.holder.PreviewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreviewAdapter : BaseAdapter<Preview, PreviewHolder> {

    @Inject
    lateinit var inflater: LayoutInflater

    @Inject
    constructor(@ForApplication context: Context) : super(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PreviewHolder? {
        return PreviewHolder(inflater.inflate(R.layout.preview, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder?, position: Int) {
        val preview = content[position]

        holder?.title?.text = preview.title
        holder?.authorName?.text = preview.authorName
        holder?.sharesCount?.text = preview.sharesCount.toString()
        holder?.commentsCount?.text = preview.commentsCount.toString()

        Picasso.with(context)
                .load(preview.imageUrl)
                .placeholder(R.drawable.preview_background)
                .into(holder!!.image)
    }
}