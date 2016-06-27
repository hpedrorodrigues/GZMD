/*
 * Copyright 2016 Pedro Rodrigues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hpedrorodrigues.gizmodobr.activity.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.AnimationInfo
import com.hpedrorodrigues.gizmodobr.constant.BundleKey
import com.hpedrorodrigues.gizmodobr.constant.GizmodoAnimation

@Suppress("unused")
abstract class BaseTransitionActivity : AppCompatActivity() {

    protected var currentAnimation: GizmodoAnimation = GizmodoAnimation.FADE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadAnimation()
    }

    override fun finish() {
        super.finish()
        overrideTransitionWithReverse()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideTransitionWithReverse()
    }

    private fun loadAnimation() {
        if (intent != null && intent.hasExtra(BundleKey.ARG_ANIMATION)) {

            val animationOrder = intent
                    .getIntExtra(BundleKey.ARG_ANIMATION, GizmodoAnimation.FADE.order)

            currentAnimation = GizmodoAnimation.find(animationOrder)
        }
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

    protected fun startWithFade(intent: Intent) = start(GizmodoAnimation.FADE, intent)

    protected fun startWithZoom(intent: Intent) = start(GizmodoAnimation.ZOOM, intent)

    protected fun startWithSlideLeft(intent: Intent) = start(GizmodoAnimation.SLIDE_LEFT, intent)

    protected fun startWithSlideRight(intent: Intent) = start(GizmodoAnimation.SLIDE_RIGHT, intent)

    protected fun startWithSlideUp(intent: Intent) = start(GizmodoAnimation.SLIDE_UP, intent)

    protected fun startWithSlideDown(intent: Intent) = start(GizmodoAnimation.SLIDE_DOWN, intent)

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
        val intent = Intent(this, activityClass)

        val reverseAnimation = AnimationInfo.findReverseByAnimation(animation)
        intent.putExtra(BundleKey.ARG_ANIMATION, reverseAnimation.order)

        startActivity(intent)
        overrideTransition(animation)
    }

    private fun start(animation: GizmodoAnimation, intent: Intent) {
        val reverseAnimation = AnimationInfo.findReverseByAnimation(animation)
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
            else -> throw IllegalArgumentException("Invalid animation $animation")
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
            else -> throw IllegalArgumentException("Invalid animation $animation")
        }

        currentAnimation = animation
    }

    private fun overrideTransitionWithReverse() {
        overrideTransition(AnimationInfo.findReverseByAnimation(currentAnimation))
    }

    protected fun overrideTransitionWithFade() = overrideTransition(GizmodoAnimation.FADE)

    protected fun overrideTransitionWithZoom() = overrideTransition(GizmodoAnimation.ZOOM)

    protected fun overrideTransitionWithSlideLeft() = overrideTransition(GizmodoAnimation.SLIDE_LEFT)

    protected fun overrideTransitionWithSlideRight() = overrideTransition(GizmodoAnimation.SLIDE_RIGHT)

    protected fun overrideTransitionWithSlideUp() = overrideTransition(GizmodoAnimation.SLIDE_UP)

    protected fun overrideTransitionWithSlideDown() = overrideTransition(GizmodoAnimation.SLIDE_DOWN)
}