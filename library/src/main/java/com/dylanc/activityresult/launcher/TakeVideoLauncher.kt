@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.TakeVideoLauncher() = TakeVideoLauncher(this)

inline fun Fragment.TakeVideoLauncher() = TakeVideoLauncher(this)

class TakeVideoLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Bitmap>(caller, ActivityResultContracts.TakeVideo()) {
}