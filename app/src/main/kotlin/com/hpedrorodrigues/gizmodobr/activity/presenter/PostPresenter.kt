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

import android.util.Log
import android.webkit.WebSettings
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.dto.PostDTO
import com.hpedrorodrigues.gizmodobr.entity.Post
import com.hpedrorodrigues.gizmodobr.rx.Rx

class PostPresenter(view: PostView) : BasePresenter<PostView>(view) {

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
}