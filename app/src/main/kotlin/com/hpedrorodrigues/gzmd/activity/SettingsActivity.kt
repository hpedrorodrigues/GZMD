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

package com.hpedrorodrigues.gzmd.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.base.BaseActivity
import com.hpedrorodrigues.gzmd.activity.presenter.SettingsPresenter
import com.hpedrorodrigues.gzmd.activity.view.SettingsView
import com.hpedrorodrigues.gzmd.dagger.GizmodoComponent
import kotlinx.android.synthetic.main.activity_settings.*
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import rx.Subscription

class SettingsActivity : BaseActivity(), SettingsView {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        configureToolbar(toolbar as Toolbar)

        enableUpButton()

        presenter = SettingsPresenter(this)

        component().inject(presenter)

        presenter.loadValues()

        presenter.configListeners(this)
    }

    override fun screenName(): String = "Settings"

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun closeApp(): RelativeLayout = closeApp

    override fun enableAutoScroll(): RelativeLayout = enableAutoScroll

    override fun keepScreenOn(): RelativeLayout = keepScreenOn

    override fun toggleCloseTheApp(): Switch = toggleCloseTheApp

    override fun toggleEnableAutoScroll(): Switch = toggleEnableAutoScroll

    override fun toggleKeepScreenOn(): Switch = toggleKeepScreenOn

//    override fun aboutTheApp(): LinearLayout = aboutTheApp

    override fun rateTheApp(): LinearLayout = rateTheApp

    override fun shareTheApp(): LinearLayout = shareTheApp

    override fun reportABug(): LinearLayout = reportABug

    override fun ideaToImprove(): LinearLayout = ideaToImprove

    override fun sendUsYourFeedback(): LinearLayout = sendUsYourFeedback

    override fun contactUs(): LinearLayout = contactUs

    override fun scrollSpeed(): DiscreteSeekBar = scrollSpeed

    override fun textSize(): DiscreteSeekBar = textSize

    override fun openSourceLicenses(): LinearLayout = openSourceLicenses

    override fun startAboutActivity() = startWithFade(AboutActivity::class.java)

    override fun bindSubscription(subscription: Subscription) = compositeSubscription.add(subscription)
}