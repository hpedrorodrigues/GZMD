package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.activity.presenter.PostPresenter
import com.hpedrorodrigues.gizmodobr.activity.view.PostView
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.entity.Preview

class PostActivity : BaseActivity(), PostView {

    lateinit var presenter: PostPresenter

    lateinit var preview: Preview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        preview = intent.getSerializableExtra(BundleKey.ARG_PREVIEW) as Preview

        presenter = PostPresenter(this)

        component().inject(presenter)

        configureToolbar()

        enableUpButton()

        presenter.loadPost(preview.postUrl)
    }

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    override fun screenName(): String = "Post"
}