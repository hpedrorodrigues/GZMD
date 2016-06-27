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
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.extension.isBeforeLollipop
import com.hpedrorodrigues.gizmodobr.util.ColorUtil
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import com.hpedrorodrigues.gizmodobr.util.VersionInfo
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
        }

        facebook.setOnClickListener {
            GizmodoApp.openFacebookPage(this, GizmodoConstant.GIZMODO_BR_FACEBOOK_ID)
        }

        instagram.setOnClickListener {
            GizmodoApp.openInstagramProfile(this, GizmodoConstant.GIZMODO_BR_INSTAGRAM_ID)
        }

        twitter.setOnClickListener {
            GizmodoApp.openTwitterProfile(this, GizmodoConstant.GIZMODO_BR_TWITTER_ID)
        }

        shareButton.setOnClickListener { GizmodoApp.share(this) }
    }

    private fun configureStatusAndNavigationBar() {
        if (!isBeforeLollipop()) {
            val colorAccentInverseColor = resources.getColor(R.color.colorAccentInverseDark, theme)
            val darkColor = ColorUtil.getDarkerColor(colorAccentInverseColor)
            window.statusBarColor = darkColor
            window.navigationBarColor = darkColor
        }
    }
}