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

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.graphics.Palette
import android.util.Log
import android.webkit.WebSettings
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.dto.PostDTO
import com.hpedrorodrigues.gizmodobr.entity.Post
import com.hpedrorodrigues.gizmodobr.rx.Rx
import com.hpedrorodrigues.gizmodobr.util.ColorUtil
import com.hpedrorodrigues.gizmodobr.util.PaletteUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PostPresenter(view: PostView) : BasePresenter<PostView>(view) {

    @Inject
    lateinit var context: Context

    fun loadBackgroundImage(imageUrl: String) {
        Picasso.with(context).load(imageUrl).into(view.backgroundImage(), loadImageCallback())
    }

    fun loadPost(postUrl: String) {
        gizmodoNetwork
                .retrievePostByUrl(PostDTO(postUrl))
                .retry(MAX_RETRIES)
                .compose(Rx.applySchedulers<Post>())
                .subscribe(
                        {
                            Log.i("Post", it.toString())
                            loadPostBody(it.body)
                        },
                        { Log.e("Error", "", it) }
                )
    }

    fun loadPostBody(body: String) {
        val webView = view.webView()

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.setSupportZoom(true)
        webView.loadData(body, "text/html; charset=utf-8", "UTF-8")
    }

    fun loadImageCallback(): Callback = object : Callback {

        override fun onSuccess() {
            val bitmap = (view.backgroundImage().drawable as BitmapDrawable).bitmap
            Palette.from(bitmap).generate { palette ->
                val swatch = PaletteUtil.getSwatch(palette)
                view.collapsingToolbar().contentScrim = ColorDrawable(swatch.rgb)

                val darkColor = ColorUtil.getDarkerColor(swatch.rgb)
                view.linkButton().backgroundTintList = ColorStateList.valueOf(darkColor)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.window().statusBarColor = Color.TRANSPARENT
                    view.window().navigationBarColor = darkColor
                }
            }
        }

        override fun onError() {
        }
    }
}