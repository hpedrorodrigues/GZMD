package com.hpedrorodrigues.gizmodobr.crawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object JsoupManager {

    fun document(url: String): Document = Jsoup.connect(url).timeout(10000).get()
}