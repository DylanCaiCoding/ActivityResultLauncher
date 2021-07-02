package com.dylanc.activityresult.launcher.sample.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author Dylan Cai
 */
abstract class BaseActivity : AppCompatActivity() {

  protected fun toast(it: String?) {
    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
  }

  protected fun toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  protected fun showDialog(@StringRes title: Int, @StringRes message: Int, onClick: () -> Unit) {
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

  protected fun showItems(@StringRes title: Int, names: List<String>) {
    MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setItems(names.toTypedArray()) { _, _ ->
      }
      .show()
  }
}