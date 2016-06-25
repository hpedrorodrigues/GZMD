package com.hpedrorodrigues.gizmodobr.entity

class Post() : BaseEntity() {

    var title: String = ""

    var body: String = ""

    var authorName: String = ""

    var authorProfileUrl: String = ""

    var postedAt: String = ""

    var info: String = ""

    var imageUrl: String = ""

    var crawledAt: Long = 0L
}