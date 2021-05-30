@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.GetContentLauncher() = GetContentLauncher(this)

inline fun Fragment.GetContentLauncher() = GetContentLauncher(this)

class GetContentLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, Uri>(caller, ActivityResultContracts.GetContent()) {

  fun launchForImage(onActivityResult: (Uri) -> Unit) = launch("image/*", onActivityResult)

  fun launchForVideo(onActivityResult: (Uri) -> Unit) = launch("video/*", onActivityResult)

  fun launchForAudio(onActivityResult: (Uri) -> Unit) = launch("audio/*", onActivityResult)
}