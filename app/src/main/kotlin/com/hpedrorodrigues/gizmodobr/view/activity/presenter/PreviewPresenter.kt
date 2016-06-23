package com.hpedrorodrigues.gizmodobr.view.activity.presenter

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.view.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.view.adapter.PreviewAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class PreviewPresenter(view: PreviewView) : BasePresenter<PreviewView>(view) {

    companion object {

        @JvmStatic val ITEM_LEFT_TO_LOAD_MORE = 5
        @JvmStatic val MAX_RETRIES = 3L
        @JvmStatic val INITIAL_PAGE = 1
    }

    @Inject
    lateinit var adapter: PreviewAdapter

    private var page = INITIAL_PAGE

    fun configureRecyclerView() {
        view.recyclerView().setLayoutManager(LinearLayoutManager(view.recyclerView().context))

        view.recyclerView().adapter = adapter


        view.recyclerView().setRefreshingColorResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryLight,
                R.color.colorPrimaryDark
        )

        view.recyclerView().setRefreshListener({
            adapter.content.clear()
            page = INITIAL_PAGE
            loadPreviews()
        })

        view.recyclerView().setupMoreListener({ numberOfItems, numberBeforeMore, currentItemPos ->
            page += 1
            loadPreviews()
        }, ITEM_LEFT_TO_LOAD_MORE)
    }

    fun loadPreviews() {
        gizmodoNetwork.retrievePreviewByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            adapter.content = it
                            view.recyclerView().hideMoreProgress()
                        },
                        { Log.e("Error", it.message) }
                )
    }
}