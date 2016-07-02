/*
 * Copyright 2016 Pedro Rodrigues
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hpedrorodrigues.gzmd.crawler

object GizmodoUrl {

    private val BaseUrl = "http://gizmodo.uol.com.br"

    val Home = "$BaseUrl/page/%s/"
    val HandsOn = "$BaseUrl/hands-on/page/%s/"
    val Review = "$BaseUrl/review/page/%s/"
    val Special = "$BaseUrl/tag/destaques/page/%s/"
    val Game = "$BaseUrl/games/page/%s/"
}