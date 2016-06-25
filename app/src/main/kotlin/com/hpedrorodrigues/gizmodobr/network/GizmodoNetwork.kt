package com.hpedrorodrigues.gizmodobr.network

import com.hpedrorodrigues.gizmodobr.entity.Post
import com.hpedrorodrigues.gizmodobr.entity.Preview
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface GizmodoNetwork {

    @GET("/preview/{page}")
    fun retrievePreviewByPage(@Path("page") page: Int): Observable<List<Preview>>

    @POST("/post")
    fun retrievePostByUrl(@Body postUrl: String): Observable<Post>
}