package com.hpedrorodrigues.gizmodobr.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant

object GizmodoMail {

    fun sendImproveAppEmail(activity: Activity) = send(activity, R.string.suggestions)

    fun sendReportBugEmail(activity: Activity) = send(activity, R.string.new_bug)

    fun sendFeedbackEmail(activity: Activity) = send(activity, R.string.feedback)

    fun sendContactUsEmail(activity: Activity) = send(activity, R.string.contact)

    private fun send(activity: Activity, resSubjectId: Int) {
        try {

            val intent = buildEmailIntent(activity, resSubjectId)
            intent.setClassName(GizmodoConstant.GMAIL_CLASS_NAME_1, GizmodoConstant.GMAIL_CLASS_NAME_2)
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

        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(GizmodoConstant.DEVELOPER_EMAIL))

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