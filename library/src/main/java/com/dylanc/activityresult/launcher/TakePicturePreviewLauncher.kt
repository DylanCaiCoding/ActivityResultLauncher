@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.TakePicturePreviewLauncher() = TakePicturePreviewLauncher(this)

inline fun Fragment.TakePicturePreviewLauncher() = TakePicturePreviewLauncher(this)

class TakePicturePreviewLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Void, Bitmap>(caller, ActivityResultContracts.TakePicturePreview()) {

  fun launch(onActivityResult: (Bitmap) -> Unit) = launch(null, onActivityResult)
}