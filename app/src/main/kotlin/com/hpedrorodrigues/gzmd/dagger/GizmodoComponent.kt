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

package com.hpedrorodrigues.gzmd.dagger

import com.hpedrorodrigues.gzmd.activity.*
import com.hpedrorodrigues.gzmd.activity.presenter.PostPresenter
import com.hpedrorodrigues.gzmd.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gzmd.activity.presenter.SettingsPresenter
import com.hpedrorodrigues.gzmd.adapter.PreviewAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GizmodoModule::class))
interface GizmodoComponent {

    fun inject(application: GizmodoApplication)

    fun inject(previewActivity: PreviewActivity)

    fun inject(aboutActivity: AboutActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(postActivity: PostActivity)

    fun inject(previewAdapter: PreviewAdapter)

    fun inject(previewPresenter: PreviewPresenter)

    fun inject(postPresenter: PostPresenter)

    fun inject(settingsPresenter: SettingsPresenter)
}