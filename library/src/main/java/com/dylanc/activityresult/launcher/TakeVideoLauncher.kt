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

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.TakeVideo
import androidx.core.content.FileProvider
import java.io.File

/**
 * @author Dylan Cai
 */
class TakeVideoLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Bitmap>(caller, TakeVideo()) {

  fun launch(callback: ActivityResultCallback<Uri?>) {
    val file = File("${context.externalCacheDir}${File.separator}${System.currentTimeMillis()}.mp4")
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } else {
      Uri.fromFile(file)
    }
    launch(uri, callback)
  }

  @JvmOverloads
  fun launchForMediaImage(contentValues: ContentValues = ContentValues(), callback: ActivityResultCallback<Uri?>) {
    val uri = context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)!!
    launch(uri, callback)
  }

  private fun launch(uri: Uri, callback: ActivityResultCallback<Uri?>) {
    launch(uri) {
      val size = uri.size
      if (size > 0) {
        callback.onActivityResult(uri)
      } else {
        callback.onActivityResult(null)
      }
    }
  }

  private val Uri.size: Long
    get() =
      context.contentResolver.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
          cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE))
        }else {
          0
        }
      } ?: 0
}