package com.hpedrorodrigues.gizmodobr.dagger

import com.hpedrorodrigues.gizmodobr.activity.PreviewActivity
import com.hpedrorodrigues.gizmodobr.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GizmodoModule::class))
interface GizmodoComponent {

    fun inject(application: GizmodoApplication)

    fun inject(previewActivity: PreviewActivity)

    fun inject(previewAdapter: PreviewAdapter)

    fun inject(previewPresenter: PreviewPresenter)
}