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

package com.hpedrorodrigues.gzmd.adapter.holder

import android.view.View
import android.widget.TextView
import com.hpedrorodrigues.gzmd.R
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