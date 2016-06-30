package com.hpedrorodrigues.gzmd.crawler

object GizmodoUrl {

    private val BaseUrl = "http://gizmodo.uol.com.br"

    val Home = "$BaseUrl/page/%s/"
    val HandsOn = "$BaseUrl/hands-on/page/%s/"
    val Review = "$BaseUrl/review/page/%s/"
    val Special = "$BaseUrl/tag/destaques/page/%s/"
    val Game = "$BaseUrl/games/page/%s/"
}