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

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract

/**
 * @author Dylan Cai
 */

val Context.isLocationEnabled: Boolean
  get() = (getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    .isProviderEnabled(LocationManager.GPS_PROVIDER)

class EnableLocationLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Unit, Boolean>(caller, EnableLocationContract(caller)) {

  fun launch(callback: ActivityResultCallback<Boolean>) = launch(null, callback)

  suspend fun launchForResult() = launchForResult(null)

  fun launchForFlow() = launchForFlow(null)

  override fun launch(input: Unit?, callback: ActivityResultCallback<Boolean>) {
    if (!context.isLocationEnabled) {
      super.launch(input, callback)
    } else {
      callback.onActivityResult(true)
    }
  }
}

class EnableLocationContract(private val caller: ActivityResultCaller) :
  ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    caller.context.isLocationEnabled
}