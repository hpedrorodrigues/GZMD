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

package com.hpedrorodrigues.gzmd.crawler

import com.hpedrorodrigues.gzmd.dto.PostDTO
import com.hpedrorodrigues.gzmd.entity.Post
import com.hpedrorodrigues.gzmd.entity.Preview
import com.hpedrorodrigues.gzmd.network.ModeView
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