package com.hpedrorodrigues.gizmodobr.crawler

import com.hpedrorodrigues.gizmodobr.entity.Preview
import org.jsoup.select.Elements
import java.util.*

object PreviewCrawler : AbstractCrawler() {

    fun findByPage(baseUrl: String, page: Int): List<Preview> {
        var count = 0
        return extractElements(baseUrl, page)
                .map { element ->
                    count++
                    extractPreview(element, count)
                }
    }

    private fun extractElements(baseUrl: String, page: Int): List<Elements> {
        val document = JsoupManager.document(baseUrl.replace("%s", page.toString()))
        var count = 1
        var elements = document.select("#maincontent #loop-chamadas div:nth-child($count)")

        val allElements = ArrayList<Elements>()

        while (!document.select("#maincontent #loop-chamadas div:nth-child(${count})").html().trim().isEmpty()) {
            allElements.add(elements)
            count += 1
            elements = document.select("#maincontent #loop-chamadas div:nth-child($count)")
        }

        return allElements
                .filter { element -> element.attr("id").startsWith("post-") && element.hasClass("post") }
    }

    private fun extractPreview(elements: Elements, count: Int): Preview {
        val preview = Preview()

        preview.title = elements.select(".title-1000 > a").attr("title").trim()

        preview.shortBody = elements.select(".chamada-contents .chamada-intro > a").text().trim()

        preview.info = elements.select(".chamada-contents .chamada-intro .autor").text().trim()

        val imageElem = imageElement(elements, count)

        if (imageElem != null) {
            preview.imageUrl = imageElem.attr("src").trim()
        }

        preview.postUrl = elements.select(".title-1000 > a").attr("href").trim()

        preview.tagName = elements.select(".chamada-thumb .chamada-cat > a").text().trim()

        preview.tagUrl = elements.select(".chamada-thumb .chamada-cat > a").attr("href").trim()

        preview.authorName = elements
                .select(".chamada-contents .chamada-intro .autor .por > a").text().trim()

        preview.postedAt = postedAt(preview.info, preview.authorName)

        preview.authorProfileUrl = elements
                .select(".chamada-contents .chamada-intro .autor .por > a").attr("href").trim()

        preview.commentsCount = commentsCount(elements)

        preview.sharesCount = sharesCount(elements)

        preview.crawledAt = Date().time

        return preview
    }
}