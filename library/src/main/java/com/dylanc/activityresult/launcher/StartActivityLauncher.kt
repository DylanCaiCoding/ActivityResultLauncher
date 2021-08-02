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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.os.bundleOf
import com.dylanc.callbacks.Callback2

/**
 * @author Dylan Cai
 */
class StartActivityLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Intent, ActivityResult>(caller, StartActivityForResult()) {

  inline fun <reified T : Activity> launch(vararg pairs: Pair<String, *>, onActivityResult: Callback2<Int, Intent?>) {
    launch(T::class.java, bundleOf(*pairs), onActivityResult)
  }

  @JvmOverloads
  fun <T : Activity> launch(clazz: Class<T>, extras: Bundle? = null, onActivityResult: Callback2<Int, Intent?>) {
    val intent = Intent(context, clazz)
    extras?.let { intent.putExtras(it) }
    launch(intent, onActivityResult)
  }

  fun launch(intent: Intent, onActivityResult: Callback2<Int, Intent?>) =
    launch(intent) { result -> onActivityResult(result.resultCode, result.data) }

  suspend inline fun <reified T : Activity> launchForResult(vararg pairs: Pair<String, *>) =
    launchForResult<T>(bundleOf(*pairs))

  suspend inline fun <reified T : Activity> launchForResult(extras: Bundle? = null) =
    launchForResult(Intent<T>(extras))

  inline fun <reified T : Activity> launchForFlow(vararg pairs: Pair<String, *>) =
    launchForFlow<T>(bundleOf(*pairs))

  inline fun <reified T : Activity> launchForFlow(extras: Bundle? = null) =
    launchForFlow(Intent<T>(extras))

  inline fun <reified T : Activity> Intent(extras: Bundle? = null) =
    Intent(context, T::class.java).apply { extras?.let { putExtras(it) } }
}