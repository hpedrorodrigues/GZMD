package com.hpedrorodrigues.gzmd.crawler

import com.hpedrorodrigues.gzmd.entity.Post
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.*

object PostCrawler : AbstractCrawler() {

    fun find(postUrl: String): Post = extractPost(postUrl)

    private fun extractPost(postUrl: String): Post {
        val document = JsoupManager.document(postUrl)

        val post = Post()

        post.title = document.select("h1").text()

        post.info = document.select(".social-title").text()

        post.body = BodyExtractor.extract(document)

        post.crawledAt = Date().time

        return post
    }
}

object BodyExtractor {

    private val mainContentSelector = "#maincontent > .post"
    private val contentContainerSelector = "#content > .container > div > .entry-content"
    private val contentPostSelector = ".content > .post > .post-content"

    fun extract(document: Document): String {
        if (isValidSelector(document, mainContentSelector)) {

            return extractByMainContent(document)
        } else if (isValidSelector(document, contentContainerSelector)) {

            return extractByContentContainer(document)
        } else if (isValidSelector(document, contentPostSelector)) {

            return extractByContentPost(document)
        } else {

            return "Empty Body"
        }
    }

    private fun isValidSelector(document: Document, selector: String): Boolean {
        val select = document.select(selector)
        return select != null && !select.isEmpty()
    }

    private fun isValidElement(element: Element?): Boolean = element != null && !element.text().isEmpty() && !element.hasClass("after-entry")

    private fun extractByMainContent(document: Document): String {
        val list = ArrayList<Element>()

        val root = document.select(mainContentSelector)

        val children = root.first().children()

        var count = 0
        while (count < children.size && !children[count].hasAttr("class")) {
            val element = children[count]
            if (isValidElement(element)) {
                list.add(element)
            }
            count += 1
        }

        val body = list.map { it.text() }
        return if (body.isEmpty()) "" else body.reduce { a, b -> a + b }
    }

    private fun extractByContentContainer(document: Document): String {
        val list = ArrayList<Element>()

        val root = document.select(contentContainerSelector)

        var rootCount = 0
        while (rootCount < root.size) {
            val child = root[rootCount]

            val children = child.children()

            var count = 0
            while (count < children.size) {
                val element = children[count]
                if (isValidElement(element)) {
                    list.add(element)
                }
                count += 1
            }

            rootCount += 1
        }

        val body = list.map { it.text() }
        return if (body.isEmpty()) "" else body.reduce { a, b -> a + b }
    }

    private fun extractByContentPost(document: Document): String {
        val list = ArrayList<Element>()

        val root = document.select(contentPostSelector)

        var rootCount = 0
        while (rootCount < root.size) {
            val child = root[rootCount]

            val children = child.children()

            var count = 0
            while (count < children.size) {
                val element = children[count]
                if (isValidElement(element)) {
                    list.add(element)
                }
                count += 1
            }

            rootCount += 1
        }

        val body = (if (list.size > 5) list.drop(1).dropLast(4) else list).map { it.text() }
        return if (body.isEmpty()) "" else body.reduce { a, b -> a + b }
    }
}