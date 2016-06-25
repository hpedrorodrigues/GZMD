package com.hpedrorodrigues.gizmodobr.adapter.holder

import android.view.View
import android.widget.TextView
import com.hpedrorodrigues.gizmodobr.R
import com.lid.lib.LabelImageView

class PreviewHolder(view: View) : BaseHolder(view) {

    lateinit var title: TextView
    lateinit var authorName: TextView
    lateinit var postedAt: TextView
    lateinit var sharesCount: TextView
    lateinit var commentsCount: TextView
    lateinit var image: LabelImageView

    override fun createView(view: View) {
        title = view.findViewById(R.id.title) as TextView
        authorName = view.findViewById(R.id.author_name) as TextView
        postedAt = view.findViewById(R.id.post_at) as TextView
        sharesCount = view.findViewById(R.id.shares_count) as TextView
        commentsCount = view.findViewById(R.id.comments_count) as TextView
        image = view.findViewById(R.id.preview_image) as LabelImageView
    }
}