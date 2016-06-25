package com.hpedrorodrigues.gizmodobr.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.CompoundButton
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import com.hpedrorodrigues.gizmodobr.preferences.GizmodoPreferences
import com.hpedrorodrigues.gizmodobr.util.GizmodoApp
import com.hpedrorodrigues.gizmodobr.util.GizmodoMail
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    @Inject
    lateinit var gizmodoPreferences: GizmodoPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadValues()

        configListeners()
    }

    override fun screenName(): String = "Settings Screen"

    override fun injectMembers(component: GizmodoComponent) = component.inject(this)

    private fun loadValues() {
        toggleCloseTheApp.isChecked = gizmodoPreferences.getBoolean(GizmodoConstant.ASK_TO_EXIT)
    }

    private fun configListeners() {
        close_app.setOnClickListener {
            val isChecked = toggleCloseTheApp.isChecked
            gizmodoPreferences.putBoolean(GizmodoConstant.ASK_TO_EXIT, !isChecked)
            toggleCloseTheApp.isChecked = !isChecked
        }

        toggleCloseTheApp.setOnCheckedChangeListener {
            compoundButton: CompoundButton, isChecked: Boolean ->
            gizmodoPreferences.putBoolean(GizmodoConstant.ASK_TO_EXIT, isChecked)
        }

        about_the_app.setOnClickListener { startWithFade(AboutActivity::class.java) }

        rate_the_app.setOnClickListener { GizmodoApp.view(this) }

        share_the_app.setOnClickListener { GizmodoApp.share(this) }

        report_a_bug.setOnClickListener { GizmodoMail.sendReportBugEmail(this) }

        idea_to_improve.setOnClickListener { GizmodoMail.sendImproveAppEmail(this) }

        send_us_your_feedback.setOnClickListener { GizmodoMail.sendFeedbackEmail(this) }

        contact_us.setOnClickListener { GizmodoMail.sendContactUsEmail(this) }
    }
}