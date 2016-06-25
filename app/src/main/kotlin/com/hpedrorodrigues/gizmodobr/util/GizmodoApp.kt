package com.hpedrorodrigues.gizmodobr.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.hpedrorodrigues.gizmodobr.R
import com.hpedrorodrigues.gizmodobr.constant.GizmodoConstant

object GizmodoApp {

    fun view(activity: Activity) {
        val packageName = activity.packageName
        val intent = Intent(Intent.ACTION_VIEW)

        intent.data = Uri.parse(GizmodoConstant.PLAY_STORE_APP_URL + packageName)

        if (!isIntentAvailable(activity, intent)) {
            intent.data = Uri.parse(GizmodoConstant.PLAY_STORE_WEB_URL + packageName)
        }

        activity.startActivity(intent)
    }

    fun share(activity: Activity) {
        val message = activity.getString(R.string.share_app_message,
                GizmodoConstant.PLAY_STORE_WEB_URL + activity.packageName)

        val intent = Intent()

        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.type = GizmodoConstant.TEXT_PLAIN_TYPE

        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.choose_app)))
    }

    fun openBrowser(activity: Activity, url: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun openFacebookPage(activity: Activity, pageId: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val packageManager = activity.packageManager

        val appUrl = "${GizmodoConstant.FACEBOOK_PAGE_APP_URL}$pageId"
        val webUrl = "${GizmodoConstant.FACEBOOK_PAGE_WEB_URL}$pageId"
        val appWebModalUrl = "${GizmodoConstant.FACEBOOK_PAGE_APP_WEB_MODAL_URL}$webUrl"

        try {
            val facebookVersionCode = packageManager.getPackageInfo(
                    GizmodoConstant.FACEBOOK_CLASS_NAME, 0).versionCode

            if (facebookVersionCode >= GizmodoConstant.FACEBOOK_CHANGE_VERSION) {
                intent.data = Uri.parse(appWebModalUrl)
            } else {
                intent.data = Uri.parse(appUrl)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            intent.data = Uri.parse(webUrl)
        }

        activity.startActivity(intent)
    }

    fun openInstagramProfile(activity: Activity, profileId: String) {
        val appUrl = "${GizmodoConstant.INSTAGRAM_APP_URL}$profileId"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appUrl))
                .setPackage(GizmodoConstant.INSTAGRAM_CLASS_NAME);

        if (isIntentAvailable(activity, intent)) {

            activity.startActivity(intent);
        } else {

            val webUrl = "${GizmodoConstant.INSTAGRAM_WEB_URL}$profileId"
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
        }
    }

    fun openTwitterProfile(activity: Activity, profileId: String) {
        val uri = Uri.parse("${GizmodoConstant.TWITTER_WEB_URL}$profileId")
        activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun isIntentAvailable(activity: Activity, intent: Intent): Boolean {
        val packageManager = activity.packageManager
        val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return list.size > 0
    }
}