package com.hpedrorodrigues.gizmodobr.entity

import java.util.Date

class Preview() : BaseEntity() {

    var title: String = ""

    var shortBody: String = ""

    var info: String = ""

    var imageUrl: String = ""

    var postUrl: String = ""

    var postedAt: String = ""

    var tagName: String = ""

    var tagUrl: String = ""

    var authorName: String = ""

    var authorProfileUrl: String = ""

    var commentsCount: Int = 0

    var sharesCount: Int = 0

    var crawledAt: Long = 0L

    fun crawledAtAsDate() = Date(crawledAt)
}