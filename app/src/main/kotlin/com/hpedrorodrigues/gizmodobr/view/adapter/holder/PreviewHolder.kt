package com.hpedrorodrigues.gizmodobr.view.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hpedrorodrigues.gizmodobr.R

class PreviewHolder(view: View) : BaseHolder(view) {

    lateinit var title: TextView
    lateinit var authorName: TextView
    lateinit var sharesCount: TextView
    lateinit var commentsCount: TextView
    lateinit var image: ImageView

    override fun createView(view: View) {
        title = view.findViewById(R.id.title) as TextView
        authorName = view.findViewById(R.id.author_name) as TextView
        sharesCount = view.findViewById(R.id.shares_count) as TextView
        commentsCount = view.findViewById(R.id.comments_count) as TextView
        image = view.findViewById(R.id.preview_image) as ImageView
    }
}