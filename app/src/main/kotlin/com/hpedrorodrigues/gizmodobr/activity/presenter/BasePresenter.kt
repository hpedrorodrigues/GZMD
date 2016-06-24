package com.hpedrorodrigues.gizmodobr.activity.presenter

import com.hpedrorodrigues.gizmodobr.network.GizmodoNetwork
import com.hpedrorodrigues.gizmodobr.activity.view.BaseView
import javax.inject.Inject

abstract class BasePresenter<T>(val view: T) where T : BaseView {

    @Inject
    protected lateinit var gizmodoNetwork: GizmodoNetwork
}