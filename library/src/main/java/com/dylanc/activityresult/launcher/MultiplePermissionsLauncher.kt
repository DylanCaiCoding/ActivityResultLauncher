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

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

inline fun Fragment.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

class MultiplePermissionsLauncher(private val caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, Map<String, Boolean>>(caller, ActivityResultContracts.RequestMultiplePermissions()) {

  fun launch(vararg permissions: String, onActivityResult: (Map<String, Boolean>) -> Unit) {
    launch(arrayOf(*permissions), onActivityResult)
  }

  @JvmOverloads
  fun launch(
    vararg permissions: String,
    onAllGranted: () -> Unit,
    onDenied: (List<String>) -> Unit,
    onShowRationale: ((List<String>) -> Unit)? = null
  ) {
    launch(*permissions) { result ->
      if (result.containsValue(false)) {
        val deniedList = result.filter { !it.value }.map { it.key }
        val map = deniedList.groupBy { permission ->
          if (caller.shouldShowRequestPermissionRationale(permission)) DENIED else EXPLAINED
        }
        map[EXPLAINED]?.let { onShowRationale?.invoke(it) ?: onDenied(it) }
        map[DENIED]?.let { onDenied(it) }
      } else {
        onAllGranted()
      }
    }
  }

  companion object {
    private const val DENIED = "DENIED"
    private const val EXPLAINED = "EXPLAINED"
  }
}