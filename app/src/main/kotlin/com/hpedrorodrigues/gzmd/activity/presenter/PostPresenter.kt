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

package com.hpedrorodrigues.gzmd.activity.presenter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.AppBarLayout
import android.support.v7.graphics.Palette
import android.util.TypedValue
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.view.PostView
import com.hpedrorodrigues.gzmd.constant.GizmodoConstant
import com.hpedrorodrigues.gzmd.constant.PreferenceKey
import com.hpedrorodrigues.gzmd.crawler.Crawler
import com.hpedrorodrigues.gzmd.dto.PostDTO
import com.hpedrorodrigues.gzmd.entity.Post
import com.hpedrorodrigues.gzmd.entity.Preview
import com.hpedrorodrigues.gzmd.extension.isBeforeLollipop
import com.hpedrorodrigues.gzmd.listener.AppBarStateChangeListener
import com.hpedrorodrigues.gzmd.logger.Logger
import com.hpedrorodrigues.gzmd.logger.MyAnswer
import com.hpedrorodrigues.gzmd.manager.NestedScrollViewManager
import com.hpedrorodrigues.gzmd.rx.Rx
import com.hpedrorodrigues.gzmd.util.ColorUtil
import com.hpedrorodrigues.gzmd.util.GizmodoApp
import com.hpedrorodrigues.gzmd.util.PaletteUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PostPresenter(view: PostView) : BasePresenter<PostView>(view) {

    @Inject
    lateinit var context: Context

    private var imageColor: Int? = null

    private lateinit var post: Post

    var nestedScrollViewManager: NestedScrollViewManager? = null
        private set

    var cancelAutoScroll: Boolean = false

    fun loadBackgroundImage(imageUrl: String) {
        Picasso.with(context).load(imageUrl).into(view.backgroundImage(), loadImageCallback())
    }

    fun configureShareButton(activity: Activity, url: String) {
        view.shareButton().setOnClickListener {
            GizmodoApp.shareMessage(activity, activity.getString(R.string.shared_by, url))
            MyAnswer.logShare("Post shared", url)
        }
    }

    fun loadPostCompleted() = view.hideProgress()

    fun loadPost(preview: Preview) {
        view.showProgress()

        val subscription = gizmodoNetwork
                .retrievePostByUrl(PostDTO(preview.postUrl))
                .compose(Rx.applySchedulers<Post>())
                .subscribe(
                        {
                            post = it
                            loadInternalPost(preview, post)
                            loadPostCompleted()
                        },
                        {
                            Logger.e("Error", it)
                            MyAnswer.log("Error loading post from server", preview.postUrl)
                            retryLoadPost(preview)
                        }
                )

        view.bindSubscription(subscription)
    }

    fun retryLoadPost(preview: Preview) {
        view.showProgress()

        val subscription = Crawler
                .retrievePostByUrl(PostDTO(preview.postUrl))
                .compose(Rx.applySchedulers<Post>())
                .subscribe(
                        {
                            post = it
                            loadInternalPost(preview, post)
                            loadPostCompleted()
                        },
                        {
                            Logger.e("Error", it)
                            showSnackbar(view.backgroundImage(), R.string.error_trying_to_load_posts)
                            loadPostCompleted()
                        }
                )

        view.bindSubscription(subscription)
    }

    private fun loadInternalPost(preview: Preview, post: Post) {
        view.titleView().text = preview.title

        view.infoView().text = preview.info

        view.textView().text = post.body

        val textSize = preferences.getLong(PreferenceKey.TEXT_SIZE, GizmodoConstant.DEFAULT_TEXT_SIZE)
        view.textView().setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

        configureNestedViewScrolling()
    }

    fun configureNestedViewScrolling() {
        nestedScrollViewManager = NestedScrollViewManager(
                view.nestedScrollView(),
                view.appBar(),
                view.coordinatorLayout(),
                preferences.getLong(PreferenceKey.SCROLL_SPEED, GizmodoConstant.DEFAULT_SCROLL_SPEED)
        )

        nestedScrollViewManager?.enableAutoScroll()

        nestedScrollViewManager?.cancelAutoScroll = cancelAutoScroll
    }

    fun enableScroll(enabled: Boolean) {
        cancelAutoScroll = !enabled
        nestedScrollViewManager?.cancelAutoScroll = !enabled
    }

    fun loadImageCallback(): Callback = object : Callback {

        override fun onSuccess() {
            val bitmap = (view.backgroundImage().drawable as BitmapDrawable).bitmap

            if (bitmap != null && !bitmap.isRecycled) {

                Palette.Builder(bitmap).generate { palette ->

                    if (palette != null) {

                        val swatch = PaletteUtil.getSwatch(palette)
                        view.collapsingToolbar().contentScrim = ColorDrawable(swatch.rgb)

                        val darkColor = ColorUtil.getDarkerColor(swatch.rgb)
                        view.shareButton().backgroundTintList = ColorStateList.valueOf(darkColor)

                        imageColor = darkColor

                        if (!isBeforeLollipop()) {
                            view.window().navigationBarColor = darkColor
                        }

                        configureAppBar()
                    }
                }
            }
        }

        override fun onError() {
        }
    }

    fun configureAppBar() {
        if (!isBeforeLollipop()) {

            view.appBar().addOnOffsetChangedListener(object : AppBarStateChangeListener() {

                override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                    if ((state.equals(State.COLLAPSED) || state.equals(State.IDLE)) && imageColor != null) {
                        view.window().statusBarColor = imageColor!!
                    } else {
                        view.window().statusBarColor = Color.TRANSPARENT
                    }
                }
            })
        }
    }
}