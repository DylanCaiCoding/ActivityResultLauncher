package com.dylanc.activityresult.launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract

/**
 * @author Dylan Cai
 */
class AppDetailsSettingsLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<Unit, Unit>(caller, AppDetailsSettingsContract()) {

  @JvmOverloads
  fun launch(callback: ActivityResultCallback<Unit> = ActivityResultCallback<Unit> {}) =
    launch(null, callback)
}

class AppDetailsSettingsContract : ActivityResultContract<Unit, Unit>() {
  override fun createIntent(context: Context, input: Unit?) =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
      data = Uri.fromParts("package", context.packageName, null)
    }

  override fun parseResult(resultCode: Int, intent: Intent?) {}
}