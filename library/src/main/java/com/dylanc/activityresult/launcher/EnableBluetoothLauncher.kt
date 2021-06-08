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
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresPermission

/**
 * @author Dylan Cai
 */
class EnableBluetoothLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Unit, Boolean>(caller, EnableBluetoothContract()) {

  @RequiresPermission(Manifest.permission.BLUETOOTH)
  fun launch(callback: ActivityResultCallback<Boolean>) {
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
      launch(null, callback)
    } else {
      callback.onActivityResult(false)
    }
  }
}

class EnableBluetoothContract : ActivityResultContract<Unit, Boolean>() {

  override fun createIntent(context: Context, input: Unit?) =
    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

  override fun parseResult(resultCode: Int, intent: Intent?) =
    resultCode == Activity.RESULT_OK
}