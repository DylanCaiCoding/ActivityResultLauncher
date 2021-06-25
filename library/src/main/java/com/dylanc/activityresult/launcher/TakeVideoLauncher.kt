/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.activityresult.launcher

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.TakeVideo
import androidx.core.content.FileProvider
import com.dylanc.callbacks.Callback2
import java.io.File

/**
 * @author Dylan Cai
 */
class TakeVideoLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Bitmap>(caller, TakeVideo()) {

  fun launch(onActivityResult: Callback2<Uri?, File?>) {
    val file = File("${context.externalCacheDir}${File.separator}${System.currentTimeMillis()}.mp4")
    val uri = file.toUri(context)
    launch(uri) {
      if (file.length() > 0) {
        onActivityResult(uri, file)
      } else {
        onActivityResult(null, null)
      }
    }
  }
}