/*
 * Copyright 2016 Pedro Rodrigues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hpedrorodrigues.gizmodobr.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.adapter.holder.PreviewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PreviewAdapter : BaseAdapter<Preview, PreviewHolder> {

    @Inject
    lateinit var inflater: LayoutInflater

    @Inject
    constructor() : super()

    var onPreviewClick: OnPreviewClick? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PreviewHolder? {
        return PreviewHolder(inflater.inflate(R.layout.preview, parent, false))
    }

    override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
        val preview = content[position]

        holder.title.text = preview.title
        holder.authorName.text = preview.authorName
        holder.postedAt.text = preview.postedAt
        holder.sharesCount.text = preview.sharesCount.toString()
        holder.commentsCount.text = preview.commentsCount.toString()

        holder.image.labelText = preview.tagName.toUpperCase()

        holder.view.setOnClickListener { onPreviewClick?.onClick(preview) }

        Picasso.with(holder.image.context)
                .load(preview.imageUrl)
                .placeholder(R.drawable.preview_background)
                .into(holder.image)
    }

    interface OnPreviewClick {

        fun onClick(preview: Preview)
    }
}