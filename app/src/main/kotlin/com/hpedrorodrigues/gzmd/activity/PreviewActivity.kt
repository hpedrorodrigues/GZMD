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

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.base.BaseActivity
import com.hpedrorodrigues.gzmd.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gzmd.activity.view.PreviewView
import com.hpedrorodrigues.gzmd.constant.BundleKey
import com.hpedrorodrigues.gzmd.constant.PreferenceKey
import com.hpedrorodrigues.gzmd.dagger.GizmodoComponent
import com.hpedrorodrigues.gzmd.entity.Preview
import com.hpedrorodrigues.gzmd.logger.MyAnswer
import com.hpedrorodrigues.gzmd.network.ModeView
import com.hpedrorodrigues.gzmd.observable.NetworkStateObservable
import com.malinskiy.superrecyclerview.SuperRecyclerView
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.splash.*
import kotlinx.android.synthetic.main.without_network.*
import rx.Subscription
import java.util.*

class PreviewActivity : BaseActivity(), PreviewView {

    lateinit var presenter: PreviewPresenter

    private var backPressedOnce: Boolean = false

    private var modeView: ModeView = ModeView.Home

    private var networkStateObserver: Observer = Observer { observable, data ->
        val hasConnection = connectionService.hasConnection()
        reloadWithoutNetworkLayout(hasConnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        configureToolbar(toolbar)

        presenter = PreviewPresenter(this)

        component().inject(presenter)

        presenter.configureRecyclerView()

        presenter.configureFabTop()

        presenter.loadPreviews()

        NetworkStateObservable.addObserver(networkStateObserver)

        showProgress()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.preview, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        loadModeView(menu)

        return when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                menu.findItem(R.id.menu_night_mode_system).isChecked = true
                MyAnswer.log("Night mode changed", "MODE_NIGHT_FOLLOW_SYSTEM")
                true
            }
            AppCompatDelegate.MODE_NIGHT_AUTO -> {
                menu.findItem(R.id.menu_night_mode_auto).isChecked = true
                MyAnswer.log("Night mode changed", "MODE_NIGHT_AUTO")
                true
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                menu.findItem(R.id.menu_night_mode_night).isChecked = true
                MyAnswer.log("Night mode changed", "MODE_NIGHT_YES")
                true
            }
            AppCompatDelegate.MODE_NIGHT_NO -> {
                menu.findItem(R.id.menu_night_mode_day).isChecked = true
                MyAnswer.log("Night mode changed", "MODE_NIGHT_NO")
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
//            R.id.action_about -> {
//                startWithFade(AboutActivity::class.java)
//                true
//            }
            R.id.action_refresh -> {
                presenter.reloadPreviews()
                MyAnswer.log("Action refresh triggered", "Screen", screenName())
                true
            }
            R.id.menu_home -> {
                setModeView(ModeView.Home, item)
                MyAnswer.log("Section changed", "Home")
                true
            }
            R.id.menu_special -> {
                setModeView(ModeView.Special, item)
                MyAnswer.log("Section changed", "Special")
                true
            }
            R.id.menu_hands_on -> {
                setModeView(ModeView.HandsOn, item)
                MyAnswer.log("Section changed", "HandsOn")
                true
            }
            R.id.menu_review -> {
                setModeView(ModeView.Review, item)
                MyAnswer.log("Section changed", "Review")
                true
            }
            R.id.menu_game -> {
                setModeView(ModeView.Game, item)
                MyAnswer.log("Section changed", "Game")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        NetworkStateObservable.deleteObserver(networkStateObserver)
        super.onDestroy()
    }

    override fun recyclerView(): SuperRecyclerView = superRecyclerView

    override fun fabTop(): FloatingActionButton = fabTop

    override fun appBar(): AppBarLayout = appBar

    override fun modeView(): ModeView = modeView

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Preview - Main"

    override fun onPreviewClick(preview: Preview) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(BundleKey.ARG_PREVIEW, preview)
        startWithFade(intent)
    }

    override fun onBackPressed() {
        if (preferences.getBoolean(PreferenceKey.ASK_TO_EXIT)) {
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

    override fun reloadWithoutNetworkLayout(hasConnection: Boolean) {
        if (hasConnection) {
            withoutNetwork.visibility = View.GONE
        } else {
            withoutNetwork.visibility = View.VISIBLE
            withoutNetwork.startAnimation(AnimationUtils.loadAnimation(this, R.anim.jump))
        }
    }

    override fun showProgress() {
        superRecyclerView.visibility = View.GONE
        splash.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        superRecyclerView.visibility = View.VISIBLE
        splash.visibility = View.GONE
    }

    private fun setModeView(modeView: ModeView, item: MenuItem) {
        item.isChecked = !item.isChecked
        this.modeView = modeView
        presenter.reloadPreviewsWithProgress()
    }

    private fun loadModeView(menu: Menu) {
        val menuItem = menu.findItem(R.id.menu_home)
        if (modeView.equals(ModeView.Home) && menuItem != null) {
            menuItem.isChecked = true
        }
    }

    override fun bindSubscription(subscription: Subscription) = compositeSubscription.add(subscription)
}