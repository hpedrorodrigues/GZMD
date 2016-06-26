package com.hpedrorodrigues.gizmodobr.activity.presenter

import android.util.Log
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
                        { Log.i("Post", it.toString()) },
                        { Log.e("Error", "", it) }
                )
    }
}