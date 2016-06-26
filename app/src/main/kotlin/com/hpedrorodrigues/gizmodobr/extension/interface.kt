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

package com.hpedrorodrigues.gizmodobr.extension

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpedrorodrigues.gizmodobr.adapter.PreviewAdapter
import com.hpedrorodrigues.gizmodobr.entity.Preview

fun broadcastReceiver(callback: (Context, Intent?) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) = callback(context, intent)
}

fun previewClick(callback: (Preview) -> Unit) = object : PreviewAdapter.OnPreviewClick {
    override fun onClick(preview: Preview) = callback(preview)
}