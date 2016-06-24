package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.presenter.PreviewPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PreviewView
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.malinskiy.superrecyclerview.SuperRecyclerView
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : BaseActivity(), PreviewView {

    lateinit var presenter: PreviewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        presenter = PreviewPresenter(this)

        component().inject(presenter)

        presenter.configureRecyclerView()

        presenter.configureFabTop()

        presenter.loadPreviews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun recyclerView(): SuperRecyclerView = superRecyclerView

    override fun fabTop(): FloatingActionButton = fabTop

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Main Screen"
}