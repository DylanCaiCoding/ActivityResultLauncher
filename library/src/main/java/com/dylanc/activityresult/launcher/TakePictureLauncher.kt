@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.TakePictureLauncher() = TakePictureLauncher(this)

inline fun Fragment.TakePictureLauncher() = TakePictureLauncher(this)

class TakePictureLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Boolean>(caller, ActivityResultContracts.TakePicture()) {
}