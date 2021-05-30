@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

inline fun Fragment.MultiplePermissionsLauncher() = MultiplePermissionsLauncher(this)

class MultiplePermissionsLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, Map<String, Boolean>>(caller, ActivityResultContracts.RequestMultiplePermissions()) {

  fun launch(vararg permissions: String, onActivityResult: (Map<String, Boolean>) -> Unit) {
    launch(arrayOf(*permissions), onActivityResult)
  }
}