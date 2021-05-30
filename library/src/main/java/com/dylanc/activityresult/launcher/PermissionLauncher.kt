@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment


inline fun ComponentActivity.PermissionLauncher() = PermissionLauncher(this)

inline fun Fragment.PermissionLauncher() = PermissionLauncher(this)

class PermissionLauncher(
  caller: ActivityResultCaller,
  private val shouldShowRequestPermissionRationale: (String) -> Boolean
) : BaseActivityResultLauncher<String, Boolean>(caller, ActivityResultContracts.RequestPermission()) {

  constructor(activity: ComponentActivity) :
      this(activity, { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) })

  constructor(fragment: Fragment) : this(
    fragment, {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        fragment.shouldShowRequestPermissionRationale(it)
      else false
    }
  )

  fun launch(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
    onShowRationale: () -> Unit = {}
  ) {
    launch(permission) {
      when {
        it -> onGranted()
        shouldShowRequestPermissionRationale(permission) -> onShowRationale()
        else -> onDenied()
      }
    }
  }
}