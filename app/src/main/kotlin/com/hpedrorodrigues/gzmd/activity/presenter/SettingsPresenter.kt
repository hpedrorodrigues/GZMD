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

package com.hpedrorodrigues.gzmd.activity.presenter

import android.app.Activity
import android.widget.CompoundButton
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.activity.SettingsActivity
import com.hpedrorodrigues.gzmd.activity.view.SettingsView
import com.hpedrorodrigues.gzmd.constant.GizmodoConstant
import com.hpedrorodrigues.gzmd.constant.PreferenceKey
import com.hpedrorodrigues.gzmd.logger.MyAnswer
import com.hpedrorodrigues.gzmd.util.GizmodoApp
import com.hpedrorodrigues.gzmd.util.GizmodoMail
import de.psdev.licensesdialog.LicensesDialogFragment
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar

class SettingsPresenter(view: SettingsView) : BasePresenter<SettingsView>(view) {

    fun loadValues() {
        view.toggleCloseTheApp().isChecked = preferences.getBoolean(PreferenceKey.ASK_TO_EXIT)
        view.toggleEnableAutoScroll().isChecked = preferences.getBoolean(PreferenceKey.ENABLE_AUTO_SCROLL)
        view.toggleKeepScreenOn().isChecked = preferences.getBoolean(PreferenceKey.KEEP_SCREEN_ON)
        view.scrollSpeed().progress = preferences
                .getLong(PreferenceKey.SCROLL_SPEED, GizmodoConstant.DEFAULT_SCROLL_SPEED).toInt()
    }

    fun configListeners(activity: Activity) {
        view.closeApp().setOnClickListener {
            val isChecked = !view.toggleCloseTheApp().isChecked
            preferences.putBoolean(PreferenceKey.ASK_TO_EXIT, isChecked)
            view.toggleCloseTheApp().isChecked = isChecked

            MyAnswer.log("Close app check changed by container", isChecked.toString())
        }

        view.toggleCloseTheApp().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.ASK_TO_EXIT, isChecked)

            MyAnswer.log("Close app check changed by switch", isChecked.toString())
        }

        view.enableAutoScroll().setOnClickListener {
            val isChecked = !view.toggleEnableAutoScroll().isChecked
            preferences.putBoolean(PreferenceKey.ENABLE_AUTO_SCROLL, isChecked)
            view.toggleEnableAutoScroll().isChecked = isChecked

            MyAnswer.log("Enable auto acroll check changed by container", isChecked.toString())
        }

        view.toggleEnableAutoScroll().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.ENABLE_AUTO_SCROLL, isChecked)

            MyAnswer.log("Enable auto acroll check changed by switch", isChecked.toString())
        }

        view.keepScreenOn().setOnClickListener {
            val isChecked = !view.toggleKeepScreenOn().isChecked
            preferences.putBoolean(PreferenceKey.KEEP_SCREEN_ON, isChecked)
            view.toggleKeepScreenOn().isChecked = isChecked

            MyAnswer.log("Keep screen on check changed by container", isChecked.toString())
        }

        view.toggleKeepScreenOn().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.KEEP_SCREEN_ON, isChecked)

            MyAnswer.log("Keep screen on check changed by switch", isChecked.toString())
        }

//        view.aboutTheApp().setOnClickListener { view.startAboutActivity() }

        view.rateTheApp().setOnClickListener {
            GizmodoApp.view(activity)

            MyAnswer.log("Rate the app action triggered")
        }

        view.shareTheApp().setOnClickListener {
            GizmodoApp.share(activity)

            MyAnswer.logShare("Share the app action triggered")
        }

        view.reportABug().setOnClickListener {
            GizmodoMail.sendReportBugEmail(activity)

            MyAnswer.log("Report a bug action triggered")
        }

        view.ideaToImprove().setOnClickListener {
            GizmodoMail.sendImproveAppEmail(activity)

            MyAnswer.log("Idea to improve action triggered")
        }

        view.sendUsYourFeedback().setOnClickListener {
            GizmodoMail.sendFeedbackEmail(activity)

            MyAnswer.log("Send us your feedback action triggered")
        }

        view.contactUs().setOnClickListener {
            GizmodoMail.sendContactUsEmail(activity)

            MyAnswer.log("Contact us action triggered")
        }

        view.openSourceLicenses().setOnClickListener {
            val dialog = LicensesDialogFragment.Builder(activity).setNotices(R.raw.notices).build()
            dialog.show((activity as SettingsActivity).supportFragmentManager, null)

            MyAnswer.log("Open source licenses action triggered")
        }

        view.scrollSpeed()
                .setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
                    override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {
                        view.toggleEnableAutoScroll().isChecked = true
                        preferences.putLong(PreferenceKey.SCROLL_SPEED, seekBar.progress.toLong())

                        MyAnswer.log("Scroll speed changed", seekBar.progress.toString())
                    }

                    override fun onStartTrackingTouch(seekBar: DiscreteSeekBar?) {
                    }

                    override fun onProgressChanged(seekBar: DiscreteSeekBar?, value: Int,
                                                   fromUser: Boolean) {
                    }
                })
    }
}