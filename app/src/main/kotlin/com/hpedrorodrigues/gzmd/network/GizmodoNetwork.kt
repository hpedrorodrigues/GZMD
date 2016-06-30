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

package com.hpedrorodrigues.gzmd.network

import com.hpedrorodrigues.gzmd.dto.PostDTO
import com.hpedrorodrigues.gzmd.entity.Post
import com.hpedrorodrigues.gzmd.entity.Preview
import retrofit2.http.*
import rx.Observable

interface GizmodoNetwork {

    @GET("/preview/{page}")
    fun retrievePreviewByPage(@Path("page") page: Int, @Query("view") view: ModeView): Observable<List<Preview>>

    @POST("/post")
    fun retrievePostByUrl(@Body postDTO: PostDTO): Observable<Post>
}