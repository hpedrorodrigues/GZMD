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

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.base.BaseActivity
import com.hpedrorodrigues.gizmodobr.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.constant.BroadcastActionKey
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.entity.Preview
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import com.malinskiy.superrecyclerview.SuperRecyclerView
import kotlinx.android.synthetic.main.activity_preview.*
import javax.inject.Inject

class PreviewActivity : BaseActivity(), PreviewView {

    lateinit var presenter: PreviewPresenter

    private var backPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        configureToolbar(toolbar)

        presenter = PreviewPresenter(this)

        component().inject(presenter)

        presenter.configureRecyclerView()

        presenter.configureFabTop()

        presenter.loadPreviews()

        startWithFade(SplashScreenActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.preview, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                menu.findItem(R.id.menu_night_mode_system).isChecked = true
                true
            }
            AppCompatDelegate.MODE_NIGHT_AUTO -> {
                menu.findItem(R.id.menu_night_mode_auto).isChecked = true
                true
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                menu.findItem(R.id.menu_night_mode_night).isChecked = true
                true
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                menu.findItem(R.id.menu_night_mode_day).isChecked = true
                true
            }
            else -> super.onPrepareOptionsMenu(menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startWithFade(SettingsActivity::class.java)
                true
            }
            R.id.action_about -> {
                startWithFade(AboutActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun recyclerView(): SuperRecyclerView = superRecyclerView

    override fun fabTop(): FloatingActionButton = fabTop

    override fun appBar(): AppBarLayout = appBar

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Preview - Main"

    override fun sendPreviewLoadedBroadcast() {
        val intent = Intent(BroadcastActionKey.PREVIEW_LOADED)
        sendBroadcast(intent)
    }

    override fun onPreviewClick(preview: Preview) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(BundleKey.ARG_PREVIEW, preview)
        startWithFade(intent)
    }

    override fun onBackPressed() {
        if (preferences.getBoolean(GizmodoConstant.ASK_TO_EXIT)) {
            if (backPressedOnce) {

                super.onBackPressed()
                overrideTransitionWithFade()
            } else {

                backPressedOnce = true
                Snackbar
                        .make(superRecyclerView, R.string.back_again_to_exit, Snackbar.LENGTH_SHORT)
                        .show()
                Handler().postDelayed({ backPressedOnce = false }, 2000L)
            }
        } else {

            super.onBackPressed()
            overrideTransitionWithFade()
        }
    }
}