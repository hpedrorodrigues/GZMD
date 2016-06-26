package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.presenter.PostPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent

class PostActivity : BaseActivity(), PostView {

    lateinit var presenter: PostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        presenter = PostPresenter(this)

        component().inject(presenter)

        configureToolbar()

        enableUpButton()

        presenter.loadPost()
    }

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Post"
}