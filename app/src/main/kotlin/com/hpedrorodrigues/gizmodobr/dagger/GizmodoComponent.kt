package com.hpedrorodrigues.gizmodobr.dagger

import com.hpedrorodrigues.gizmodobr.activity.AboutActivity
import com.hpedrorodrigues.gizmodobr.activity.PreviewActivity
import com.hpedrorodrigues.gizmodobr.activity.SettingsActivity
import com.hpedrorodrigues.gizmodobr.activity.SplashScreenActivity
import com.hpedrorodrigues.gizmodobr.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GizmodoModule::class))
interface GizmodoComponent {

    fun inject(application: GizmodoApplication)

    fun inject(previewActivity: PreviewActivity)

    fun inject(splashScreenActivity: SplashScreenActivity)

    fun inject(aboutActivity: AboutActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(previewAdapter: PreviewAdapter)

    fun inject(previewPresenter: PreviewPresenter)
}