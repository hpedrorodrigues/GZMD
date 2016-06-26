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

package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.view.Window
import android.webkit.WebView
import android.widget.ImageView
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.activity.presenter.PostPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.entity.Preview
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : BaseActivity(), PostView {

    lateinit var presenter: PostPresenter

    lateinit var preview: Preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        preview = intent.getSerializableExtra(BundleKey.ARG_PREVIEW) as Preview

        presenter = PostPresenter(this)

        component().inject(presenter)

        configureToolbar(toolbar)

        enableUpButton()

        presenter.loadBackgroundImage(preview.imageUrl)

        presenter.loadPost(preview.postUrl)
    }

    override fun backgroundImage(): ImageView = backgroundImage

    override fun collapsingToolbar(): CollapsingToolbarLayout = collapsingToolbar

    override fun linkButton(): FloatingActionButton = linkButton

    override fun window(): Window = window

    override fun webView(): WebView = webView

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Post"
}