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

package com.hpedrorodrigues.gzmd.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import com.hpedrorodrigues.gzmd.R
import com.hpedrorodrigues.gzmd.constant.GizmodoConstant

object GizmodoMail {

    fun sendImproveAppEmail(activity: Activity) = send(activity, R.string.suggestions)

    fun sendReportBugEmail(activity: Activity) = send(activity, R.string.new_bug)

    fun sendFeedbackEmail(activity: Activity) = send(activity, R.string.feedback)

    fun sendContactUsEmail(activity: Activity) = send(activity, R.string.contact)

    private fun send(activity: Activity, resSubjectId: Int) {
        try {

            val intent = buildEmailIntent(activity, resSubjectId)
            intent.setClassName(GizmodoConstant.GMAIL_PACKAGE_NAME, GizmodoConstant.GMAIL_CLASS_NAME)
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {

            val intent = buildEmailIntent(activity, resSubjectId)

            if (isIntentAvailable(activity, intent)) {
                activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.choose_app)))
            }
        }
    }

    private fun buildEmailIntent(activity: Activity, resSubjectId: Int): Intent {
        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(GizmodoConstant.EMAIL))

        val subject = "${GizmodoConstant.DEFAULT_SUBJECT} ${activity.getString(resSubjectId)}"

        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, DeviceInfo.DETAILS)
        intent.type = GizmodoConstant.DEFAULT_EMAIL_TYPE

        return intent
    }

    private fun isIntentAvailable(activity: Activity, intent: Intent): Boolean {
        val packageManager = activity.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }
}