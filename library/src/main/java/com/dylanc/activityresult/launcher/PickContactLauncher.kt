@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.PickContactLauncher() = PickContactLauncher(this)

inline fun Fragment.PickContactLauncher() = PickContactLauncher(this)

class PickContactLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Void, Uri>(caller, ActivityResultContracts.PickContact()) {

  fun launch(onActivityResult: (Uri) -> Unit) = launch(null, onActivityResult)
}