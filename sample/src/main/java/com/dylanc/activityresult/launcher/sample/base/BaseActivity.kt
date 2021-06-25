package com.dylanc.activityresult.launcher.sample.base

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File

/**
 * @author Dylan Cai
 */
abstract class BaseActivity : AppCompatActivity() {

  protected fun toast(it: String?) {
    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
  }

  protected fun showDialog(title: String, message: String, onClick: () -> Unit) {
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

//  protected fun createUri(filename: String): Uri {
//    val file = File(externalCacheDir, filename)
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//      FileProvider.getUriForFile(application, "$packageName.provider", file)
//    } else {
//      Uri.fromFile(file)
//    }
//  }
}