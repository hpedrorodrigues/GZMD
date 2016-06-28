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

import android.support.design.widget.Snackbar
import android.view.View
import com.hpedrorodrigues.gizmodobr.network.GizmodoNetwork
import com.hpedrorodrigues.gizmodobr.activity.view.BaseView
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import com.hpedrorodrigues.gizmodobr.service.ConnectionService
import javax.inject.Inject

abstract class BasePresenter<T>(val view: T) where T : BaseView {

    @Inject
    protected lateinit var gizmodoNetwork: GizmodoNetwork

    @Inject
    lateinit var connectionService: ConnectionService

    @Inject
    lateinit var preferences: GizmodoPreferences

    protected fun showSnackbar(view: View, resId: Int) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show()
    }
}