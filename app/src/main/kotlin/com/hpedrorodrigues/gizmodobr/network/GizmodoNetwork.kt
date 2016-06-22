package com.hpedrorodrigues.gizmodobr.network

import com.hpedrorodrigues.gizmodobr.entity.Preview
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

public interface GizmodoNetwork {

    @GET("/preview/{page}")
    public fun retrievePreviewByPage(@Path("page") page: Int): Observable<List<Preview>>
}