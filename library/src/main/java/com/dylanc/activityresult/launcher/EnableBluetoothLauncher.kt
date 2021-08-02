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
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import com.dylanc.callbacks.Callback0
import com.dylanc.callbacks.Callback1

/**
 * @author Dylan Cai
 */
class EnableBluetoothLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Unit, Boolean>(caller, EnableBluetoothContract()) {

  private val requestPermissionLauncher = RequestPermissionLauncher(caller)
  private val enableLocationLauncher = EnableLocationLauncher(caller)

  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launch(callback: ActivityResultCallback<Boolean>) = launch(null, callback)

  @RequiresPermission(Manifest.permission.BLUETOOTH)
  suspend fun launchForResult() = launchForResult(null)

  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launchForFlow() = launchForFlow(null)

  @RequiresPermission(Manifest.permission.BLUETOOTH)
  override fun launch(input: Unit?, callback: ActivityResultCallback<Boolean>) {
    if (BluetoothAdapter.getDefaultAdapter()?.isEnabled != true) {
      super.launch(input, callback)
    } else {
      callback.onActivityResult(true)
    }
  }

  @JvmOverloads
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launch(
    onBluetoothEnabled: ActivityResultCallback<Boolean>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null
  ) {
    launch {
      if (it) {
        requestPermissionLauncher.launch(
          Manifest.permission.ACCESS_FINE_LOCATION,
          onGranted = { onBluetoothEnabled.onActivityResult(true) },
          onDenied = onPermissionDenied,
          onExplainRequest = onExplainRequestPermission
        )
      } else {
        onBluetoothEnabled.onActivityResult(false)
      }
    }
  }

  @JvmOverloads
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launchAndEnableLocation(
    @StringRes enablePositionReason: Int,
    onLocationEnabled: ActivityResultCallback<Boolean>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null,
    onBluetoothDisabled: Callback0? = null
  ) =
    launchAndEnableLocation(
      context.getString(enablePositionReason), onLocationEnabled, onPermissionDenied,
      onExplainRequestPermission, onBluetoothDisabled
    )

  @JvmOverloads
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launchAndEnableLocation(
    enablePositionReason: String,
    onLocationEnabled: ActivityResultCallback<Boolean>,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null,
    onBluetoothDisabled: Callback0? = null
  ) =
    launchAndEnableLocation(
      { onLocationEnabled.onActivityResult(true) },
      onPermissionDenied, onExplainRequestPermission, onBluetoothDisabled,
      {
        Toast.makeText(context, enablePositionReason, Toast.LENGTH_SHORT).show()
        it.launch(onLocationEnabled)
      })

  @JvmOverloads
  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launchAndEnableLocation(
    onLocationEnabled: Callback0,
    onPermissionDenied: Callback1<AppDetailsSettingsLauncher>,
    onExplainRequestPermission: Callback0? = null,
    onBluetoothDisabled: Callback0? = null,
    onLocationDisabled: Callback1<EnableLocationLauncher>
  ) {
    launch({
      if (it && !context.isLocationEnabled) {
        onLocationDisabled(enableLocationLauncher)
      } else if (it) {
        onLocationEnabled()
      } else {
        onBluetoothDisabled?.invoke()
      }
    }, onPermissionDenied, onExplainRequestPermission)
  }
}

class EnableBluetoothContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    resultCode == Activity.RESULT_OK
}