package com.hpedrorodrigues.gzmd.crawler

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

abstract class AbstractCrawler {

    protected fun imageElement(elements: Elements, count: Int): Element? {
        val children: Elements = elements.select(".chamada-thumb > a > img")

        if (children.size >= 1) {

            val position = if (count < children.size) count else (children.size - 1)
            return children[position]
        } else {

            return null;
        }
    }

    protected fun sharesCount(elements: Elements): Int {
        val sharesCountText = elements
                .select(".chamada-contents .social-meta .social-count").text().trim()

        return if (sharesCountText.isEmpty()) 0 else sharesCountText.toInt()
    }

    protected fun commentsCount(elements: Elements): Int {
        val commentsCountText = elements
                .select(".chamada-contents .social-meta .social-comments > a > span").text().trim()

        return if (commentsCountText.isEmpty()) 0 else commentsCountText.toInt()
    }

    protected fun postedAt(info: String, authorName: String): String {
        val index = info.indexOf(authorName) + authorName.length
        return info.substring(index + 1, info.length)
    }
}