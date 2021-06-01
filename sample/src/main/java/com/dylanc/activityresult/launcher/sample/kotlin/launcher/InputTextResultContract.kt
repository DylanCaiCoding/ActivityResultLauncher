package com.dylanc.activityresult.launcher.sample.kotlin.launcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import java.io.Serializable

/**
 * @author Dylan Cai
 */

class InputTextConfig(
  val name: String,
  val title: String = name,
  val hint: String? = null,
  val value: String? = null,
  val listener: OnFilterValueListener? = null
)

interface OnFilterValueListener : Serializable {
  fun onFilterValue(value: String): Boolean
}

class InputTextResultContract : ActivityResultContract<InputTextConfig, String>() {
  override fun createIntent(context: Context, input: InputTextConfig) =
    Intent(context, InputTextActivity::class.java).apply {
      putExtra(KEY_NAME, input.name)
      putExtra(KEY_TITLE, input.title)
      putExtra(KEY_HINT, input.hint)
      putExtra(KEY_VALUE, input.value)
      putExtra(KEY_LISTENER, input.listener)
    }

  override fun parseResult(resultCode: Int, intent: Intent?) =
    if (resultCode == Activity.RESULT_OK && intent != null) {
      intent.getStringExtra(KEY_VALUE)
    } else {
      null
    }

  companion object {
    const val KEY_TITLE = "title"
    const val KEY_NAME = "name"
    const val KEY_HINT = "hint"
    const val KEY_VALUE = "value"
    const val KEY_LISTENER = "listener"
  }
}