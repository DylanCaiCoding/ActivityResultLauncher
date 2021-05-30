@file:Suppress("unused", "NOTHING_TO_INLINE", "FunctionName")

package com.dylanc.activityresult.launcher

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment

/**
 * @author Dylan Cai
 */

inline fun ComponentActivity.ActivityResultLauncher() = ActivityResultLauncher(this)

inline fun Fragment.ActivityResultLauncher() = ActivityResultLauncher(this)

inline fun <I, O> ComponentActivity.ActivityResultLauncher(activityResultContract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, activityResultContract)

inline fun <I, O> Fragment.ActivityResultLauncher(activityResultContract: ActivityResultContract<I, O>) =
  BaseActivityResultLauncher(this, activityResultContract)

class ActivityResultLauncher(private val caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Intent, ActivityResult>(caller, StartActivityForResult()) {

  inline fun <reified T : Activity> launch(noinline onActivityResult: (ActivityResult) -> Unit) {
    launch(T::class.java, onActivityResult)
  }

  fun <T : Activity> launch(clazz: Class<T>, onActivityResult: (ActivityResult) -> Unit) {
    val context = when (caller) {
      is ComponentActivity -> caller
      is Fragment -> caller.requireContext()
      else -> throw IllegalAccessException()
    }
    launch(Intent(context, clazz), onActivityResult)
  }
}