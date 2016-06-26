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