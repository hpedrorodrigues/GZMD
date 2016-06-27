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

package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.CompoundButton
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import com.hpedrorodrigues.gizmodobr.util.GizmodoMail
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    @Inject
    lateinit var gizmodoPreferences: GizmodoPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        configureToolbar(toolbar as Toolbar)

        enableUpButton()

        loadValues()

        configListeners()
    }

    override fun screenName(): String = "Settings"

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    private fun loadValues() {
        toggleCloseTheApp.isChecked = gizmodoPreferences.getBoolean(GizmodoConstant.ASK_TO_EXIT)
        toggleEnableAutoScroll.isChecked = gizmodoPreferences.getBoolean(GizmodoConstant.ENABLE_AUTO_SCROLL)
    }

    private fun configListeners() {
        closeApp.setOnClickListener {
            val isChecked = toggleCloseTheApp.isChecked
            gizmodoPreferences.putBoolean(GizmodoConstant.ASK_TO_EXIT, !isChecked)
            toggleCloseTheApp.isChecked = !isChecked
        }

        toggleCloseTheApp.setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            gizmodoPreferences.putBoolean(GizmodoConstant.ASK_TO_EXIT, isChecked)
        }

        enableAutoScroll.setOnClickListener {
            val isChecked = !toggleEnableAutoScroll.isChecked
            gizmodoPreferences.putBoolean(GizmodoConstant.ENABLE_AUTO_SCROLL, isChecked)
            toggleEnableAutoScroll.isChecked = isChecked
        }

        toggleEnableAutoScroll.setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            gizmodoPreferences.putBoolean(GizmodoConstant.ENABLE_AUTO_SCROLL, isChecked)
        }

        aboutTheApp.setOnClickListener { startWithFade(AboutActivity::class.java) }

        rateTheApp.setOnClickListener { GizmodoApp.view(this) }

        shareTheApp.setOnClickListener { GizmodoApp.share(this) }

        reportABug.setOnClickListener { GizmodoMail.sendReportBugEmail(this) }

        ideaToImprove.setOnClickListener { GizmodoMail.sendImproveAppEmail(this) }

        sendUsYourFeedback.setOnClickListener { GizmodoMail.sendFeedbackEmail(this) }

        contactUs.setOnClickListener { GizmodoMail.sendContactUsEmail(this) }
    }
}