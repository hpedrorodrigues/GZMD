package com.hpedrorodrigues.gizmodobr.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.flyco.labelview.LabelView
import com.hpedrorodrigues.gizmodobr.R

class PreviewHolder(view: View) : BaseHolder(view) {

    lateinit var title: TextView
    lateinit var authorName: TextView
    lateinit var tagName: LabelView
    lateinit var postAt: TextView
    lateinit var sharesCount: TextView
    lateinit var commentsCount: TextView
    lateinit var image: ImageView

    override fun createView(view: View) {
        title = view.findViewById(R.id.title) as TextView
        authorName = view.findViewById(R.id.author_name) as TextView
        tagName = view.findViewById(R.id.tag_name) as LabelView
        postAt = view.findViewById(R.id.post_at) as TextView
        sharesCount = view.findViewById(R.id.shares_count) as TextView
        commentsCount = view.findViewById(R.id.comments_count) as TextView
        image = view.findViewById(R.id.preview_image) as ImageView
    }
}