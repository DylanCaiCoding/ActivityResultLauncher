@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

inline fun ComponentActivity.OpenDocumentTreeLauncher() = OpenDocumentTreeLauncher(this)

inline fun Fragment.OpenDocumentTreeLauncher() = OpenDocumentTreeLauncher(this)

class OpenDocumentTreeLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Uri, Uri>(caller, ActivityResultContracts.OpenDocumentTree())