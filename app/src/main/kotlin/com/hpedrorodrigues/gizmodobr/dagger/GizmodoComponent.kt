package com.hpedrorodrigues.gizmodobr.dagger

import com.hpedrorodrigues.gizmodobr.view.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GizmodoModule::class))
interface GizmodoComponent {

    fun inject(application: GizmodoApplication)

    fun inject(mainActivity: MainActivity)
}