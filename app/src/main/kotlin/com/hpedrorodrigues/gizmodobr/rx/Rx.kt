package com.hpedrorodrigues.gizmodobr.rx

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object Rx {

    fun <T> applySchedulers(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { observable ->
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}