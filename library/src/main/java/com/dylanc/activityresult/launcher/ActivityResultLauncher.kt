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

@file:Suppress("unused", "NOTHING_TO_INLINE", "FunctionName")

package com.dylanc.activityresult.launcher

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

inline fun ComponentActivity.ActivityResultLauncher() = ActivityResultLauncher(this)

inline fun Fragment.ActivityResultLauncher() = ActivityResultLauncher(this)

inline fun <I, O> ComponentActivity.ActivityResultLauncher(contract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, contract)

inline fun <I, O> Fragment.ActivityResultLauncher(contract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, contract)

internal fun ActivityResultCaller.shouldShowRequestPermissionRationale(permission: String) =
  when (this) {
    is Activity -> ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    is Fragment -> ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)
    else -> false
  }

class ActivityResultLauncher(private val caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Intent, ActivityResult>(caller, StartActivityForResult()) {

  inline fun <reified T : Activity> launch(noinline onActivityResult: (ActivityResult) -> Unit) {
    launch(T::class.java, onActivityResult)
  }

  fun <T : Activity> launch(clazz: Class<T>, onActivityResult: (ActivityResult) -> Unit) {
    val context = when (caller) {
      is ComponentActivity -> caller
      is Fragment -> caller.requireContext()
      else -> throw IllegalAccessException()
    }
    launch(Intent(context, clazz), onActivityResult)
  }
}