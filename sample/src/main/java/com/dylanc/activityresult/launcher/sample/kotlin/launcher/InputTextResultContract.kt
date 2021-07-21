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

package com.dylanc.activityresult.launcher.sample.kotlin.launcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import java.io.Serializable

/**
 * @author Dylan Cai
 */
class InputTextResultContract : ActivityResultContract<InputTextRequest, String>() {
  override fun createIntent(context: Context, input: InputTextRequest) =
    Intent(context, InputTextActivity::class.java).apply {
      putExtra(KEY_NAME, input.name)
      putExtra(KEY_TITLE, input.title)
      putExtra(KEY_HINT, input.hint)
      putExtra(KEY_VALUE, input.value)
      putExtra(KEY_LISTENER, input.listener)
    }

  override fun parseResult(resultCode: Int, intent: Intent?): String? =
    if (resultCode == Activity.RESULT_OK) intent?.getStringExtra(KEY_VALUE) else null

  companion object {
    const val KEY_TITLE = "title"
    const val KEY_NAME = "name"
    const val KEY_HINT = "hint"
    const val KEY_VALUE = "value"
    const val KEY_LISTENER = "listener"
  }
}