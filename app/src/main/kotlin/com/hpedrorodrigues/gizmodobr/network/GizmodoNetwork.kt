package com.hpedrorodrigues.gizmodobr.network

import com.hpedrorodrigues.gizmodobr.entity.Preview
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

public interface GizmodoNetwork {

    @GET("/preview/{page}")
    public fun retrievePreviewByPage(@Path("page") page: Int): Observable<List<Preview>>
}