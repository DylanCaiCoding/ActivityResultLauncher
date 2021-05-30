@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.GetMultipleContentLauncher() = GetMultipleContentLauncher(this)

inline fun Fragment.GetMultipleContentLauncher() = GetMultipleContentLauncher(this)

class GetMultipleContentLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, List<Uri>>(caller, ActivityResultContracts.GetMultipleContents()) {

  fun launchForImage(onActivityResult: (List<Uri>) -> Unit) = launch("image/*", onActivityResult)

  fun launchForVideo(onActivityResult: (List<Uri>) -> Unit) = launch("video/*", onActivityResult)

  fun launchForAudio(onActivityResult: (List<Uri>) -> Unit) = launch("audio/*", onActivityResult)
}