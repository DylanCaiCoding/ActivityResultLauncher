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

package com.dylanc.activityresult.launcher

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

internal fun ActivityResultCaller.shouldShowRequestPermissionRationale(permission: String) =
  when (this) {
    is Activity -> ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
    is Fragment -> ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)
    else -> false
  }

val ActivityResultCaller.context
  get() = when (this) {
    is Activity -> this
    is Fragment -> this.requireContext()
    else -> throw IllegalArgumentException("The constructor's ActivityResultCaller argument must be Activity or Fragment.")
  }