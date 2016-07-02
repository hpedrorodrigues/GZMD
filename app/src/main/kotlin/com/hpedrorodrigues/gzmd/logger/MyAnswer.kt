package com.hpedrorodrigues.gzmd.logger

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.crashlytics.android.answers.ShareEvent

object MyAnswer {

    fun instance() = Answers.getInstance()

    fun log(message: String) {
        instance().logCustom(CustomEvent(message))
    }

    fun log(message: String, value: String) {
        instance().logCustom(CustomEvent(message).putCustomAttribute("Value", value))
    }

    fun log(message: String, attrName: String, value: String) {
        instance().logCustom(CustomEvent(message).putCustomAttribute(attrName, value))
    }

    fun logShare(message: String, value: String) {
        instance().logShare(
                ShareEvent().putMethod("Android Provider")
                        .putContentName(message).putCustomAttribute("Value", value)
        )
    }

    fun logShare(message: String) {
        instance().logShare(ShareEvent().putMethod("Android Provider").putContentName(message))
    }
}