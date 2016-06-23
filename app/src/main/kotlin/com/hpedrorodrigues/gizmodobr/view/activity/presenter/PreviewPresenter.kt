package com.hpedrorodrigues.gizmodobr.view.activity.presenter

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.hpedrorodrigues.gizmodobr.view.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.view.adapter.PreviewAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class PreviewPresenter(view: PreviewView) : BasePresenter<PreviewView>(view) {

    @Inject
    lateinit var adapter: PreviewAdapter

    fun configureRecyclerView() {
        view.recyclerView().setLayoutManager(LinearLayoutManager(view.recyclerView().context))

        view.recyclerView().adapter = adapter
    }

    fun loadPreviews() {
        gizmodoNetwork.retrievePreviewByPage(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { adapter.content = it },
                        { Log.e("Error", it.message) }
                )
    }
}