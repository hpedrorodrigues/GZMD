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

package com.hpedrorodrigues.gizmodobr.activity.presenter

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.extension.previewClick
import com.hpedrorodrigues.gizmodobr.listener.RecyclerViewScrollListener
import com.hpedrorodrigues.gizmodobr.logger.Logger
import com.hpedrorodrigues.gizmodobr.rx.Rx
import javax.inject.Inject

class PreviewPresenter(view: PreviewView) : BasePresenter<PreviewView>(view) {

    companion object {

        @JvmStatic val ITEM_LEFT_TO_LOAD_MORE = 5
        @JvmStatic val INITIAL_PAGE = 1
        @JvmStatic val ITEM_POSITION_TO_SCROLL = 5
    }

    @Inject
    lateinit var adapter: PreviewAdapter

    private var page = INITIAL_PAGE

    fun configureRecyclerView() {
        view.recyclerView().setLayoutManager(LinearLayoutManager(view.recyclerView().context))

        view.recyclerView().setOnScrollListener(RecyclerViewScrollListener(view.fabTop()))

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
            reloadPreviews()
        }

        view.recyclerView().setupMoreListener({ numberOfItems, numberBeforeMore, currentItemPos ->
            page++
            loadPreviews()
        }, ITEM_LEFT_TO_LOAD_MORE)

        adapter.onPreviewClick = previewClick { view.onPreviewClick(it) }
    }

    fun configureFabTop() {
        view.fabTop().setOnClickListener {
            val recyclerView = view.recyclerView().recyclerView

            val firstItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

            if (firstItemPosition > ITEM_POSITION_TO_SCROLL) {
                recyclerView.scrollToPosition(ITEM_POSITION_TO_SCROLL)
            }

            recyclerView.smoothScrollToPosition(0)
            view.appBar().setExpanded(true, true)
        }
    }

    fun reloadPreviews() {
        view.recyclerView().swipeToRefresh?.isRefreshing = true
        page = INITIAL_PAGE

        if (connectionService.hasConnection()) {
            adapter.clear()
        }

        loadPreviews()
    }

    fun loadPreviews() {
        val hasConnection = connectionService.hasConnection()
        view.reloadWithoutNetworkLayout(hasConnection)

        if (hasConnection) {
            fun onCompleted() {
                view.recyclerView().swipeToRefresh?.isRefreshing = false
                view.recyclerView().hideMoreProgress()
                view.hideProgress()
            }

            val subscription = gizmodoNetwork
                    .retrievePreviewByPage(page)
                    .retry(MAX_RETRIES)
                    .compose(Rx.applySchedulers<List<Preview>>())
                    .subscribe(
                            {
                                adapter.add(it)
                                onCompleted()
                            },
                            {

                                Logger.e("Error", it)
                                showSnackbar(view.recyclerView(), R.string.error_trying_to_load_previews)
                                onCompleted()
                            }
                    )

            view.bindSubscription(subscription)
        }
    }
}