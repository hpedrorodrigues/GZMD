package com.hpedrorodrigues.gizmodobr.crawler

import com.hpedrorodrigues.gizmodobr.dto.PostDTO
import com.hpedrorodrigues.gizmodobr.entity.Post
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.network.ModeView
import rx.Observable
import rx.lang.kotlin.observable

object Crawler {

    private fun getUrlByModel(modelView: ModeView): String {
        return when (modelView) {
            ModeView.HandsOn -> GizmodoUrl.HandsOn.toString()
            ModeView.Review -> GizmodoUrl.Review.toString()
            ModeView.Special -> GizmodoUrl.Special.toString()
            ModeView.Game -> GizmodoUrl.Game.toString()
            else -> GizmodoUrl.Home.toString()
        }
    }

    fun retrievePreviewByPage(page: Int, modelView: ModeView): Observable<List<Preview>> {
        return observable { subscriber ->
            try {
                val previews = PreviewCrawler.findByPage(getUrlByModel(modelView), page)
                subscriber.onNext(previews)
            } catch(e: Exception) {
                subscriber.onError(e)
            }

            subscriber.onCompleted()
        }
    }

    fun retrievePostByUrl(postDTO: PostDTO): Observable<Post> {
        return observable { subscriber ->
            try {
                val post = PostCrawler.find(postDTO.postUrl)
                subscriber.onNext(post)
            } catch(e: Exception) {
                subscriber.onError(e)
            }

            subscriber.onCompleted()
        }
    }
}