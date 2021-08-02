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

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import com.dylanc.callbacks.Callback0
import com.dylanc.callbacks.Callback1
import com.dylanc.callbacks.Callback2
import kotlinx.coroutines.flow.flow

/**
 * @author Dylan Cai
 */
class RequestMultiplePermissionsLauncher(private val caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, Map<String, Boolean>>(caller, RequestMultiplePermissions()) {

  private val settingsLauncher = AppDetailsSettingsLauncher(caller)

  fun launch(vararg permissions: String, onActivityResult: ActivityResultCallback<Map<String, Boolean>>) =
    launch(arrayOf(*permissions), onActivityResult)

  @JvmOverloads
  fun launch(
    vararg permissions: String,
    onAllGranted: Callback0,
    onDenied: Callback2<List<String>, AppDetailsSettingsLauncher>,
    onExplainRequest: (Callback1<List<String>>)? = null
  ) {
    launch(*permissions) { result ->
      if (result.containsValue(false)) {
        val deniedList = result.filter { !it.value }.map { it.key }
        val explainableList = deniedList.filter { caller.shouldShowRequestPermissionRationale(it) }
        if (explainableList.isNotEmpty()) {
          onExplainRequest?.invoke(explainableList) ?: onDenied(explainableList, settingsLauncher)
        } else {
          onDenied(deniedList, settingsLauncher)
        }
      } else {
        onAllGranted()
      }
    }
  }

  fun launchForFlow(vararg permissions: String) = launchForFlow(arrayOf(*permissions))

  fun launchForFlow(
    vararg permissions: String,
    onDenied: Callback2<List<String>, AppDetailsSettingsLauncher>,
    onExplainRequest: (Callback1<List<String>>)? = null
  ) = flow {
    val result = launchForResult(arrayOf(*permissions))
    if (result.containsValue(false)) {
      val deniedList = result.filter { !it.value }.map { it.key }
      val explainableList = deniedList.filter { caller.shouldShowRequestPermissionRationale(it) }
      if (explainableList.isNotEmpty()) {
        onExplainRequest?.invoke(explainableList) ?: onDenied(explainableList, settingsLauncher)
      } else {
        onDenied(deniedList, settingsLauncher)
      }
    } else {
      emit(Unit)
    }
  }
}