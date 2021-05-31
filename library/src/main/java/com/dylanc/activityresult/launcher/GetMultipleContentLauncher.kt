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

@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.Manifest
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.GetMultipleContentLauncher() = GetMultipleContentLauncher(this)

inline fun Fragment.GetMultipleContentLauncher() = GetMultipleContentLauncher(this)

class GetMultipleContentLauncher(
  caller: ActivityResultCaller
) : BaseActivityResultLauncher<String, List<Uri>>(caller, ActivityResultContracts.GetMultipleContents()) {

  private val permissionLauncher = PermissionLauncher(caller)

  @JvmOverloads
  fun launch(
    input: String,
    onActivityResult: (List<Uri>) -> Unit,
    onPermissionDenied: () -> Unit,
    onShowPermissionRationale: (() -> Boolean)? = null
  ) {
    permissionLauncher.launch(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onGranted = { launch(input, onActivityResult) },
      onPermissionDenied,
      onShowPermissionRationale
    )
  }

  @JvmOverloads
  fun launchForImage(
    onActivityResult: (List<Uri>) -> Unit,
    onPermissionDenied: () -> Unit,
    onShowPermissionRationale: (() -> Boolean)? = null
  ) = launch("image/*", onActivityResult, onPermissionDenied, onShowPermissionRationale)

  @JvmOverloads
  fun launchForVideo(
    onActivityResult: (List<Uri>) -> Unit,
    onPermissionDenied: () -> Unit,
    onShowPermissionRationale: (() -> Boolean)? = null
  ) = launch("video/*", onActivityResult, onPermissionDenied, onShowPermissionRationale)

  @JvmOverloads
  fun launchForAudio(
    onActivityResult: (List<Uri>) -> Unit,
    onPermissionDenied: () -> Unit,
    onShowPermissionRationale: (() -> Boolean)? = null
  ) = launch("audio/*", onActivityResult, onPermissionDenied, onShowPermissionRationale)
}