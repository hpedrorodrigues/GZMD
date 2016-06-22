package com.hpedrorodrigues.gizmodobr.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoApplication
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent

abstract class BaseActivity() : AppCompatActivity() {

    protected abstract fun injectMembers(component: GizmodoComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectMembers(component())
    }

    protected fun component() = (application as GizmodoApplication).component()
}