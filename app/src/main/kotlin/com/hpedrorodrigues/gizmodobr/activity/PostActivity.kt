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
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.activity.presenter.PostPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import kotlinx.android.synthetic.main.activity_post.*
import rx.Subscription

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

        presenter.configureShareButton(this, preview.postUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_open_in_browser -> {
                GizmodoApp.openBrowser(this, preview.postUrl)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.nestedScrollViewManager?.cancelAutoScroll = true
        super.onDestroy()
    }

    override fun backgroundImage(): ImageView = backgroundImage

    override fun collapsingToolbar(): CollapsingToolbarLayout = collapsingToolbar

    override fun coordinatorLayout(): CoordinatorLayout = coordinatorLayout

    override fun shareButton(): FloatingActionButton = shareButton

    override fun window(): Window = window

    override fun textView(): TextView = textView

    override fun appBar(): AppBarLayout = appBar

    override fun nestedScrollView(): NestedScrollView = nestedScrollView

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Post"

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        nestedScrollView.visibility = View.GONE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
        nestedScrollView.visibility = View.VISIBLE
    }

    override fun bindSubscription(subscription: Subscription) = compositeSubscription.add(subscription)
}