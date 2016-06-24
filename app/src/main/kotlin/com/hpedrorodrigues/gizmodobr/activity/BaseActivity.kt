package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoApplication
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent

abstract class BaseActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectMembers(component())
    }

    override fun onResume() {
        super.onResume()

        tracker().setScreenName(screenName())
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        Answers.getInstance()
                .logContentView(ContentViewEvent().putContentId("Screen:" + screenName()))
    }

    protected abstract fun injectMembers(component: GizmodoComponent)

    protected abstract fun screenName(): String

    protected fun gizmodoApplication() = application as GizmodoApplication

    protected fun component() = gizmodoApplication().component()

    protected fun tracker() = gizmodoApplication().tracker()
}