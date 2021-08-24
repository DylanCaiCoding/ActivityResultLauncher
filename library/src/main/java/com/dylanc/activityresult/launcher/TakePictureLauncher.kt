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
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.core.content.FileProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

/**
 * @author Dylan Cai
 */
class TakePictureLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Boolean>(caller, TakePicture()) {

  fun launch(callback: ActivityResultCallback<Uri?>) = launch(externalCacheUri, callback)

  @JvmOverloads
  fun launchForMediaImage(contentValues: ContentValues = ContentValues(), callback: ActivityResultCallback<Uri?>) =
    launch(mediaUriOf(contentValues), callback)

  suspend fun launchForResult() = launchForUriResult(externalCacheUri)

  suspend fun launchForMediaImageResult(contentValues: ContentValues = ContentValues()) =
    launchForUriResult(mediaUriOf(contentValues))

  fun launchForFlow() = launchForUriFlow(externalCacheUri)

  fun launchForMediaImageFlow(contentValues: ContentValues = ContentValues()) =
    launchForUriFlow(mediaUriOf(contentValues))

  private fun launch(uri: Uri, callback: ActivityResultCallback<Uri?>) {
    launch(uri) {
      if (it) {
        callback.onActivityResult(uri)
      } else {
        callback.onActivityResult(null)
      }
    }
  }

  private suspend fun launchForUriResult(uri: Uri) =
    suspendCancellableCoroutine<Uri> { continuation ->
      launch(uri) {
        if (it) {
          continuation.resume(uri)
        } else {
          continuation.cancel()
        }
      }
    }

  private fun launchForUriFlow(uri: Uri) = flow { emit(launchForUriResult(uri)) }

  private val externalCacheUri: Uri
    get() {
      val file = File("${context.externalCacheDir}${File.separator}${System.currentTimeMillis()}.jpg")
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        FileProvider.getUriForFile(context, FileProviderUtils.authority ?: "${context.packageName}.provider", file)
      } else {
        Uri.fromFile(file)
      }
    }

  private fun mediaUriOf(contentValues: ContentValues) =
    context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
}