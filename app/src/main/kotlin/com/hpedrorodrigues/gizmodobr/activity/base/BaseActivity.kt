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

package com.hpedrorodrigues.gizmodobr.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.PreferenceKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoApplication
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.extension.isAfterGingerbread
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import com.hpedrorodrigues.gizmodobr.service.ConnectionService
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

abstract class BaseActivity() : BaseTransitionActivity() {

    @Inject
    lateinit var preferences: GizmodoPreferences

    @Inject
    lateinit var connectionService: ConnectionService

    protected var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectMembers(component())

        val nightMode = preferences
                .getInt(PreferenceKey.NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        setNightMode(nightMode)
    }

    override fun onResume() {
        super.onResume()

        tracker().setScreenName(screenName())
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        Answers.getInstance()
                .logContentView(ContentViewEvent().putContentId("Screen:" + screenName()))
    }

    override fun onDestroy() {
        unsubscribeSubscriptions()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_night_mode_system -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            R.id.menu_night_mode_day -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.menu_night_mode_night -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.menu_night_mode_auto -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun injectMembers(component: GizmodoComponent)

    protected abstract fun screenName(): String

    protected fun gizmodoApplication() = application as GizmodoApplication

    protected fun component() = gizmodoApplication().component()

    protected fun tracker() = gizmodoApplication().tracker()

    protected fun unsubscribeSubscriptions() {
        compositeSubscription.unsubscribe()
        compositeSubscription = CompositeSubscription()
    }

    protected fun setNightMode(@AppCompatDelegate.NightMode nightMode: Int) {
        val isDifferent = AppCompatDelegate.getDefaultNightMode() != nightMode

        if (isDifferent) {
            AppCompatDelegate.setDefaultNightMode(nightMode)

            preferences.putInt(PreferenceKey.NIGHT_MODE, nightMode)

            if (isAfterGingerbread()) {
                recreate()
            }
        }
    }

    protected fun configureToolbar(toolbar: Toolbar) = setSupportActionBar(toolbar)

    protected fun enableUpButton() = supportActionBar?.setDisplayHomeAsUpEnabled(true)
}