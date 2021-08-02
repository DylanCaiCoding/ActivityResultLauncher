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

import android.Manifest
import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.GetMultipleContents
import com.dylanc.callbacks.Callback0
import com.dylanc.callbacks.Callback1

/**
 * @author Dylan Cai
 */
class GetMultipleContentsLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, List<Uri>>(caller, GetMultipleContents()) {

  private val permissionLauncher = RequestPermissionLauncher(caller)

  suspend fun launchForImageResult() = launchForNonEmptyResult("image/*")

  suspend fun launchForVideoResult() = launchForNonEmptyResult("video/*")

   fun launchForImageFlow() = launchForNonEmptyFlow("image/*")

   fun launchForVideoFlow() = launchForNonEmptyFlow("video/*")

  @JvmOverloads
  fun launch(
    input: String,
    onActivityResult: ActivityResultCallback<List<Uri>>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null
  ) {
    permissionLauncher.launch(
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onGranted = { launch(input, onActivityResult) },
      onPermissionDenied,
      onExplainRequestPermission
    )
  }

  @JvmOverloads
  fun launchForImage(
    onActivityResult: ActivityResultCallback<List<Uri>>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null
  ) = launch("image/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)

  @JvmOverloads
  fun launchForVideo(
    onActivityResult: ActivityResultCallback<List<Uri>>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null
  ) = launch("video/*", onActivityResult, onPermissionDenied, onExplainRequestPermission)
}