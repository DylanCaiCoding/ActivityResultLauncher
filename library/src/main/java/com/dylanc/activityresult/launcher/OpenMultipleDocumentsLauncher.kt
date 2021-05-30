@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.OpenMultipleDocumentsLauncher() = OpenMultipleDocumentsLauncher(this)

inline fun Fragment.OpenMultipleDocumentsLauncher() = OpenMultipleDocumentsLauncher(this)

class OpenMultipleDocumentsLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Array<String>, List<Uri>>(caller, ActivityResultContracts.OpenMultipleDocuments())