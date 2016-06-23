package com.hpedrorodrigues.gizmodobr.view.activity.presenter

import com.hpedrorodrigues.gizmodobr.network.GizmodoNetwork
import com.hpedrorodrigues.gizmodobr.view.activity.view.BaseView
import javax.inject.Inject

abstract class BasePresenter<T>(val view: T) where T : BaseView {

    @Inject
    protected lateinit var gizmodoNetwork: GizmodoNetwork
}