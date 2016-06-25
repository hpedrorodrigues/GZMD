package com.hpedrorodrigues.gizmodobr.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.AnimationInfo
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.constant.GizmodoAnimation
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoApplication
import com.hpedrorodrigues.gizmodobr.dagger.GizmodoComponent
import rx.subscriptions.CompositeSubscription

@Suppress("unused")
abstract class BaseActivity() : AppCompatActivity() {

    protected var currentAnimation: GizmodoAnimation = GizmodoAnimation.FADE

    protected var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectMembers(component())

        loadAnimation()
    }

    override fun onResume() {
        super.onResume()

        tracker().setScreenName(screenName())
        tracker().send(HitBuilders.ScreenViewBuilder().build())
        Answers.getInstance()
                .logContentView(ContentViewEvent().putContentId("Screen:" + screenName()))
    }

    override fun onDestroy() {
        unsubscribeSubscriptions()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun injectMembers(component: GizmodoComponent)

    protected abstract fun screenName(): String

    protected fun gizmodoApplication() = application as GizmodoApplication

    protected fun component() = gizmodoApplication().component()

    protected fun tracker() = gizmodoApplication().tracker()

    protected fun unsubscribeSubscriptions() {
        compositeSubscription.unsubscribe()
        compositeSubscription = CompositeSubscription()
    }

    private fun loadAnimation() {
        if (intent != null && intent.hasExtra(BundleKey.ARG_ANIMATION)) {

            val animationOrder = intent
                    .getIntExtra(BundleKey.ARG_ANIMATION, GizmodoAnimation.FADE.order)

            currentAnimation = GizmodoAnimation.find(animationOrder)
        }
    }

    override fun finish() {
        super.finish()
        overrideTransitionWithReverse()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideTransitionWithReverse()
    }

    protected fun <A : BaseActivity> startWithFade(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.FADE)
    }

    protected fun <A : BaseActivity> startWithZoom(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.ZOOM)
    }

    protected fun <A : BaseActivity> startWithSlideLeft(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.SLIDE_LEFT)
    }

    protected fun <A : BaseActivity> startWithSlideRight(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.SLIDE_RIGHT)
    }

    protected fun <A : BaseActivity> startWithSlideUp(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.SLIDE_UP)
    }

    protected fun <A : BaseActivity> startWithSlideDown(activityClass: Class<A>) {
        start(activityClass, GizmodoAnimation.SLIDE_DOWN)
    }

    protected fun <A : BaseActivity> startWithResultAndFade(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.FADE)
    }

    protected fun <A : BaseActivity> startWithResultAndZoom(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.ZOOM)
    }

    protected fun <A : BaseActivity> startWithResultAndSlideLeft(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.SLIDE_LEFT)
    }

    protected fun <A : BaseActivity> startWithResultAndSlideRight(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.SLIDE_RIGHT)
    }

    protected fun <A : BaseActivity> startWithResultAndSlideUp(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.SLIDE_UP)
    }

    protected fun <A : BaseActivity> startWithResultAndSlideDown(activityClass: Class<A>, requestCode: Int) {
        startWithResult(activityClass, requestCode, GizmodoAnimation.SLIDE_DOWN)
    }

    protected fun replaceFragmentWithFade(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.FADE)
    }

    protected fun replaceFragmentWithZoom(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.ZOOM)
    }

    protected fun replaceFragmentWithSlideLeft(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.SLIDE_LEFT)
    }

    protected fun replaceFragmentWithSlideRight(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.SLIDE_RIGHT)
    }

    protected fun replaceFragmentWithSlideUp(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.SLIDE_UP)
    }

    protected fun replaceFragmentWithSlideDown(containerId: Int, fragment: Fragment) {
        replaceFragment(containerId, fragment, GizmodoAnimation.SLIDE_DOWN)
    }

    private fun <A : BaseActivity> start(activityClass: Class<A>, animation: GizmodoAnimation) {
        val reverseAnimation = AnimationInfo.findReverseByAnimation(animation)
        val intent = Intent(this, activityClass)
        intent.putExtra(BundleKey.ARG_ANIMATION, reverseAnimation.order)

        startActivity(intent)
        overrideTransition(animation)
    }

    private fun <A : BaseActivity> startWithResult(
            activityClass: Class<A>, requestCode: Int, animation: GizmodoAnimation) {
        val reverseAnimation = AnimationInfo.findReverseByAnimation(animation)
        val intent = Intent(this, activityClass)
        intent.putExtra(BundleKey.ARG_ANIMATION, reverseAnimation.order)

        startActivityForResult(intent, requestCode)
        overrideTransition(animation)
    }

    private fun replaceFragment(containerId: Int, fragment: Fragment, animation: GizmodoAnimation) {
        val transaction = supportFragmentManager.beginTransaction()

        val reverseAnimation = AnimationInfo.findReverseByAnimation(animation)

        val bundle = if (fragment.arguments == null) Bundle() else fragment.arguments

        bundle.putInt(BundleKey.ARG_ANIMATION, reverseAnimation.order)

        fragment.arguments = bundle

        when (animation) {
            GizmodoAnimation.FADE ->
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            GizmodoAnimation.ZOOM ->
                transaction.setCustomAnimations(R.anim.zoom_in, R.anim.zoom_out)
            GizmodoAnimation.SLIDE_LEFT ->
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right)
            GizmodoAnimation.SLIDE_RIGHT ->
                transaction.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_right)
            GizmodoAnimation.SLIDE_UP ->
                transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
            GizmodoAnimation.SLIDE_DOWN ->
                transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
            else -> IllegalArgumentException("Invalid animation $animation")
        }

        transaction.replace(containerId, fragment).commit()
        currentAnimation = animation
    }

    private fun overrideTransition(animation: GizmodoAnimation) {
        when (animation) {
            GizmodoAnimation.FADE ->
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            GizmodoAnimation.ZOOM ->
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out)
            GizmodoAnimation.SLIDE_LEFT ->
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
            GizmodoAnimation.SLIDE_RIGHT ->
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            GizmodoAnimation.SLIDE_UP ->
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            GizmodoAnimation.SLIDE_DOWN ->
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
            else -> IllegalArgumentException("Invalid animation $animation")
        }
        currentAnimation = animation
    }

    private fun overrideTransitionWithReverse() {
        overrideTransition(AnimationInfo.findReverseByAnimation(currentAnimation))
    }

    protected fun overrideTransitionWithFade() {
        overrideTransition(GizmodoAnimation.FADE)
    }

    protected fun overrideTransitionWithZoom() {
        overrideTransition(GizmodoAnimation.ZOOM)
    }

    protected fun overrideTransitionWithSlideLeft() {
        overrideTransition(GizmodoAnimation.SLIDE_LEFT)
    }

    protected fun overrideTransitionWithSlideRight() {
        overrideTransition(GizmodoAnimation.SLIDE_RIGHT)
    }

    protected fun overrideTransitionWithSlideUp() {
        overrideTransition(GizmodoAnimation.SLIDE_UP)
    }

    protected fun overrideTransitionWithSlideDown() {
        overrideTransition(GizmodoAnimation.SLIDE_DOWN)
    }
}