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

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.dylanc.activityresult.launcher.sample.databinding.ActivityInputTextBinding
import com.dylanc.viewbinding.binding

class InputTextActivity : AppCompatActivity() {

  private val binding: ActivityInputTextBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    with(binding) {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowHomeEnabled(true)
      title = intent.getStringExtra(InputTextResultContract.KEY_TITLE)
      tvName.text = intent.getStringExtra(InputTextResultContract.KEY_NAME)
      edtValue.hint = intent.getStringExtra(InputTextResultContract.KEY_HINT)
      edtValue.setText(intent.getStringExtra(InputTextResultContract.KEY_VALUE))
      btnSave.isEnabled = edtValue.text.toString().isNotEmpty()
      edtValue.doAfterTextChanged {
        btnSave.isEnabled = edtValue.text.toString().isNotEmpty()
      }
      btnSave.setOnClickListener { onSave() }
    }
  }

  private fun onSave() {
    val listener = intent.getSerializableExtra(InputTextResultContract.KEY_LISTENER) as OnFilterValueListener?
    val isFilterValue = listener?.onFilterValue(binding.edtValue.text.toString())
    if (isFilterValue != true) {
      val intent = Intent().apply {
        putExtra(InputTextResultContract.KEY_VALUE, binding.edtValue.text.toString())
      }
      setResult(RESULT_OK, intent)
      finish()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}