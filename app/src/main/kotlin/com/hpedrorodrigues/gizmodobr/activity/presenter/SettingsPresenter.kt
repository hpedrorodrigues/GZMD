package com.hpedrorodrigues.gizmodobr.activity.presenter

import android.app.Activity
import android.widget.CompoundButton
import com.hpedrorodrigues.gizmodobr.activity.view.SettingsView
import com.hpedrorodrigues.gizmodobr.constant.PreferenceKey
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import com.hpedrorodrigues.gizmodobr.util.GizmodoMail

class SettingsPresenter(view: SettingsView) : BasePresenter<SettingsView>(view) {

    fun loadValues() {
        view.toggleCloseTheApp().isChecked = preferences.getBoolean(PreferenceKey.ASK_TO_EXIT)
        view.toggleEnableAutoScroll().isChecked = preferences.getBoolean(PreferenceKey.ENABLE_AUTO_SCROLL)
        view.toggleKeepScreenOn().isChecked = preferences.getBoolean(PreferenceKey.KEEP_SCREEN_ON)
    }

    fun configListeners(activity: Activity) {
        view.closeApp().setOnClickListener {
            val isChecked = !view.toggleCloseTheApp().isChecked
            preferences.putBoolean(PreferenceKey.ASK_TO_EXIT, isChecked)
            view.toggleCloseTheApp().isChecked = isChecked
        }

        view.toggleCloseTheApp().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.ASK_TO_EXIT, isChecked)
        }

        view.enableAutoScroll().setOnClickListener {
            val isChecked = !view.toggleEnableAutoScroll().isChecked
            preferences.putBoolean(PreferenceKey.ENABLE_AUTO_SCROLL, isChecked)
            view.toggleEnableAutoScroll().isChecked = isChecked
        }

        view.toggleEnableAutoScroll().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.ENABLE_AUTO_SCROLL, isChecked)
        }

        view.keepScreenOn().setOnClickListener {
            val isChecked = !view.toggleKeepScreenOn().isChecked
            preferences.putBoolean(PreferenceKey.KEEP_SCREEN_ON, isChecked)
            view.toggleKeepScreenOn().isChecked = isChecked
        }

        view.toggleKeepScreenOn().setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            preferences.putBoolean(PreferenceKey.KEEP_SCREEN_ON, isChecked)
        }

        view.aboutTheApp().setOnClickListener { view.startAboutActivity() }

        view.rateTheApp().setOnClickListener { GizmodoApp.view(activity) }

        view.shareTheApp().setOnClickListener { GizmodoApp.share(activity) }

        view.reportABug().setOnClickListener { GizmodoMail.sendReportBugEmail(activity) }

        view.ideaToImprove().setOnClickListener { GizmodoMail.sendImproveAppEmail(activity) }

        view.sendUsYourFeedback().setOnClickListener { GizmodoMail.sendFeedbackEmail(activity) }

        view.contactUs().setOnClickListener { GizmodoMail.sendContactUsEmail(activity) }
    }
}