package com.hpedrorodrigues.gizmodobr.activity.view

import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Switch

interface SettingsView : BaseView {

    fun closeApp(): RelativeLayout

    fun enableAutoScroll(): RelativeLayout

    fun keepScreenOn(): RelativeLayout

    fun toggleCloseTheApp(): Switch

    fun toggleEnableAutoScroll(): Switch

    fun toggleKeepScreenOn(): Switch

    fun aboutTheApp(): LinearLayout

    fun rateTheApp(): LinearLayout

    fun shareTheApp(): LinearLayout

    fun reportABug(): LinearLayout

    fun ideaToImprove(): LinearLayout

    fun sendUsYourFeedback(): LinearLayout

    fun contactUs(): LinearLayout

    fun startAboutActivity()
}