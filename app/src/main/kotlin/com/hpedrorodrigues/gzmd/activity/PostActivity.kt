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

package com.hpedrorodrigues.gzmd.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.base.BaseActivity
import com.hpedrorodrigues.gzmd.activity.presenter.PostPresenter
import com.hpedrorodrigues.gzmd.activity.view.PostView
import com.hpedrorodrigues.gzmd.constant.BundleKey
import com.hpedrorodrigues.gzmd.constant.PreferenceKey
import com.hpedrorodrigues.gzmd.dagger.GizmodoComponent
import com.hpedrorodrigues.gzmd.entity.Preview
import com.hpedrorodrigues.gzmd.logger.MyAnswer
import com.hpedrorodrigues.gzmd.util.GizmodoApp
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

        presenter.loadPost(preview)

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
                MyAnswer.log("Opened post in browser", preview.postUrl)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()

        unloadScreenOn()
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

    override fun titleView(): TextView = titleView

    override fun infoView(): TextView = infoView

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

    override fun loadScreenOn() {
        if (preferences.getBoolean(PreferenceKey.KEEP_SCREEN_ON)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun unloadScreenOn() = window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    override fun bindSubscription(subscription: Subscription) = compositeSubscription.add(subscription)
}