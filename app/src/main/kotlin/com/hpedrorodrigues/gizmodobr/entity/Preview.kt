package com.hpedrorodrigues.gizmodobr.entity

import java.util.Date

public class Preview() : BaseEntity() {

    var title: String = ""
    var shortBody: String = ""
    var info: String = ""
    var imageUrl: String = ""
    var postUrl: String = ""
    var categoryName: String = ""
    var categoryUrl: String = ""
    var authorName: String = ""
    var authorProfileUrl: String = ""
    var commentsCount: Int = 0
    var sharesCount: Int = 0
    var crawledAt: Long = 0L

    public fun crawledAtAsDate() = Date(crawledAt)

    override fun toString(): String {
        return "Preview(title='$title', shortBody='$shortBody', info='$info', imageUrl='$imageUrl', postUrl='$postUrl', categoryName='$categoryName', categoryUrl='$categoryUrl', authorName='$authorName', authorProfileUrl='$authorProfileUrl', commentsCount=$commentsCount, sharesCount=$sharesCount, crawledAt=$crawledAt)"
    }
}