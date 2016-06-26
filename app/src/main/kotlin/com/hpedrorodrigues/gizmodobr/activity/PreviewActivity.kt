package com.hpedrorodrigues.gizmodobr.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hpedrorodrigues.gizmodobr.R
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

    @Inject
    lateinit var gizmodoPreferences: GizmodoPreferences

    lateinit var presenter: PreviewPresenter

    private var backPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        configureToolbar()

        presenter = PreviewPresenter(this)

        component().inject(presenter)

        presenter.configureRecyclerView()

        presenter.configureFabTop()

        presenter.loadPreviews()

        startWithFade(SplashScreenActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
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
        if (gizmodoPreferences.getBoolean(GizmodoConstant.ASK_TO_EXIT)) {
            if (backPressedOnce) {

                super.onBackPressed()
                overrideTransitionWithFade()
            } else {

                backPressedOnce = true
                Toast.makeText(this, R.string.back_again_to_exit, Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ backPressedOnce = false }, 2000L)
            }
        } else {

            super.onBackPressed()
            overrideTransitionWithFade()
        }
    }
}