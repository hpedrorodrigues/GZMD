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
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.base.BaseActivity
import com.hpedrorodrigues.gzmd.constant.GizmodoConstant
import com.hpedrorodrigues.gzmd.dagger.GizmodoComponent
import com.hpedrorodrigues.gzmd.extension.isBeforeLollipop
import com.hpedrorodrigues.gzmd.util.ColorUtil
import com.hpedrorodrigues.gzmd.util.GizmodoApp
import com.hpedrorodrigues.gzmd.util.VersionInfo
import kotlinx.android.synthetic.main.about_body.*
import kotlinx.android.synthetic.main.activity_about.*
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var versionInfo: VersionInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        configureToolbar(toolbar as Toolbar)

        enableUpButton()

        loadVersion()

        configureListeners()

        configureStatusAndNavigationBar()
    }

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "About"

    private fun loadVersion() {
        version.text = versionInfo.getVersionName()
    }

    private fun configureListeners() {
        google_plus.setOnClickListener {
            GizmodoApp.openGPlus(this, GizmodoConstant.GIZMODO_BR_GOOGLE_PLUS_ID)
            Answers.getInstance().logCustom(CustomEvent("Opened Gizmodo Social Media: Google Plus"))
        }

        facebook.setOnClickListener {
            GizmodoApp.openFacebookPage(this, GizmodoConstant.GIZMODO_BR_FACEBOOK_ID)
            Answers.getInstance().logCustom(CustomEvent("Opened Gizmodo Social Media: Facebook"))
        }

        instagram.setOnClickListener {
            GizmodoApp.openInstagramProfile(this, GizmodoConstant.GIZMODO_BR_INSTAGRAM_ID)
            Answers.getInstance().logCustom(CustomEvent("Opened Gizmodo Social Media: Instagram"))
        }

        twitter.setOnClickListener {
            GizmodoApp.openTwitterProfile(this, GizmodoConstant.GIZMODO_BR_TWITTER_ID)
            Answers.getInstance().logCustom(CustomEvent("Opened Gizmodo Social Media: Twitter"))
        }

        shareButton.setOnClickListener {
            GizmodoApp.share(this)
            Answers.getInstance().logCustom(CustomEvent("GZMD Shared"))
        }
    }

    private fun configureStatusAndNavigationBar() {
        if (!isBeforeLollipop()) {
            val colorAccentInverseColor = resources.getColor(R.color.colorPrimary, theme)
            val darkColor = ColorUtil.getDarkerColor(colorAccentInverseColor)
            window.statusBarColor = darkColor
            window.navigationBarColor = darkColor
        }
    }
}