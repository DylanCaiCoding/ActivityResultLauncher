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

package com.dylanc.activityresult.launcher.sample

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.callbacks.Callback0
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author Dylan Cai
 */
abstract class BaseActivity : AppCompatActivity() {

  fun toast(it: String?) {
    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
  }

  fun toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  fun showDialog(@StringRes title: Int, @StringRes message: Int, onClick: Callback0) {
    MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setMessage(message)
      .setPositiveButton(android.R.string.ok) { _, _ ->
        onClick()
      }
      .setNegativeButton(android.R.string.cancel) { _, _ ->
      }
      .show()
  }

  fun showItems(@StringRes title: Int, names: List<String>) {
    MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setItems(names.toTypedArray()) { _, _ ->
      }
      .show()
  }
}