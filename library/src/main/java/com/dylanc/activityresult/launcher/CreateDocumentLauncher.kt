@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


inline fun ComponentActivity.CreateDocumentLauncher() = CreateDocumentLauncher(this)

inline fun Fragment.CreateDocumentLauncher() = CreateDocumentLauncher(this)

class CreateDocumentLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<String, Uri>(caller, ActivityResultContracts.CreateDocument())
