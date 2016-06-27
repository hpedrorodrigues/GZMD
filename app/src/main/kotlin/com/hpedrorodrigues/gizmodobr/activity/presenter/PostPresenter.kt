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

package com.hpedrorodrigues.gizmodobr.activity.presenter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.AppBarLayout
import android.support.v7.graphics.Palette
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant
import com.hpedrorodrigues.gizmodobr.dto.PostDTO
import com.hpedrorodrigues.gizmodobr.entity.Post
import com.hpedrorodrigues.gizmodobr.extension.isBeforeLollipop
import com.hpedrorodrigues.gizmodobr.listener.AppBarStateChangeListener
import com.hpedrorodrigues.gizmodobr.manager.NestedScrollViewManager
import com.hpedrorodrigues.gizmodobr.rx.Rx
import com.hpedrorodrigues.gizmodobr.util.ColorUtil
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import com.hpedrorodrigues.gizmodobr.util.PaletteUtil
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

    fun loadBackgroundImage(imageUrl: String) {
        Picasso.with(context).load(imageUrl).into(view.backgroundImage(), loadImageCallback())
    }

    fun configureShareButton(activity: Activity, url: String) {
        view.shareButton().setOnClickListener {
            GizmodoApp.shareMessage(activity, activity.getString(R.string.shared_by, url))
        }
    }

    fun loadPost(postUrl: String) {
        view.showProgress()

        gizmodoNetwork
                .retrievePostByUrl(PostDTO(postUrl))
                .retry(MAX_RETRIES)
                .compose(Rx.applySchedulers<Post>())
                .subscribe(
                        {
                            post = it
                            loadPostBodyHtml(post.bodyHtml)
                            loadPostBodyText(post.bodyText)
                            view.hideProgress()
                        },
                        {
                            Log.e("Error", "", it)
                            view.hideProgress()
                        }
                )
    }

    fun loadPostBodyText(body: String) {
        view.textView().text = body

        configureNestedViewScrolling()
    }

    fun configureNestedViewScrolling() {
        if (gizmodoPreferences.getBoolean(GizmodoConstant.ENABLE_AUTO_SCROLL)) {
            nestedScrollViewManager = NestedScrollViewManager(
                    view.nestedScrollView(),
                    view.appBar(),
                    view.coordinatorLayout()
            )

            nestedScrollViewManager?.enableAutoScroll()
        }
    }

    fun loadPostBodyHtml(body: String) {
        val webView = view.webView()
        val settings = webView.settings

        webView.clearCache(true)

        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.setSupportZoom(true)

        webView.loadData(body, "text/html; charset=utf-8", "UTF-8")

        webView.setWebViewClient(object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                webView.loadUrl("javascript:document.body.style.margin=\"8%\"; void 0")
            }
        })
    }

    fun loadImageCallback(): Callback = object : Callback {

        override fun onSuccess() {
            val bitmap = (view.backgroundImage().drawable as BitmapDrawable).bitmap
            Palette.from(bitmap).generate { palette ->
                val swatch = PaletteUtil.getSwatch(palette)
                view.collapsingToolbar().contentScrim = ColorDrawable(swatch.rgb)

                val darkColor = ColorUtil.getDarkerColor(swatch.rgb)
                view.shareButton().backgroundTintList = ColorStateList.valueOf(darkColor)

                imageColor = darkColor

                if (!isBeforeLollipop()) {
                    view.window().navigationBarColor = darkColor
                }

                view.hideProgress()

                configureAppBar()
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

    fun seeLikePage(seeLikePage: Boolean) {
        view.webView().visibility = if (seeLikePage) View.VISIBLE else View.GONE
        view.textView().visibility = if (seeLikePage) View.GONE else View.VISIBLE
    }
}