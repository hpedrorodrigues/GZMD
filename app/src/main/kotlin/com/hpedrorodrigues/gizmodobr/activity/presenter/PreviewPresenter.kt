package com.hpedrorodrigues.gizmodobr.activity.presenter

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import com.hpedrorodrigues.gizmodobr.listener.CustomScrollListener
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

        view.recyclerView().setOnScrollListener(CustomScrollListener(view.fabTop()))

        view.recyclerView().adapter = adapter

        view.recyclerView().recyclerView.setHasFixedSize(true)
        view.recyclerView().recyclerView.setItemViewCacheSize(20)
        view.recyclerView().recyclerView.isDrawingCacheEnabled = true
        view.recyclerView().recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH

        view.recyclerView().setRefreshingColorResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryLight,
                R.color.colorPrimaryDark
        )

        view.recyclerView().setRefreshListener {
            adapter.clear()
            page = INITIAL_PAGE
            loadPreviews()
        }

        view.recyclerView().setupMoreListener({ numberOfItems, numberBeforeMore, currentItemPos ->
            page++
            loadPreviews()
        }, ITEM_LEFT_TO_LOAD_MORE)
    }

    fun configureFabTop() {
        view.fabTop().setOnClickListener {
            val recyclerView = view.recyclerView().recyclerView

            val firstItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

            if (firstItemPosition > 5) {
                recyclerView.scrollToPosition(5)
            }

            recyclerView.smoothScrollToPosition(0)
        }
    }

    fun loadPreviews() {
        gizmodoNetwork
                .retrievePreviewByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(MAX_RETRIES)
                .subscribe(
                        { adapter.add(it) },
                        { Log.e("Error", it.message) },
                        {
                            view.recyclerView().swipeToRefresh?.isRefreshing = false
                            view.recyclerView().hideMoreProgress()
                            view.sendPreviewLoadedBroadcast()
                        }
                )
    }
}