package com.dylanc.activityresult.launcher.sample.kotlin.launcher

import androidx.activity.result.ActivityResultCaller
import com.dylanc.activityresult.launcher.BaseActivityResultLauncher

/**
 * @author Dylan Cai
 */

class InputTextLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<InputTextConfig, String>(caller, InputTextResultContract()) {

  fun launch(
    name: String,
    title: String = name,
    hint: String? = null,
    value: String? = null,
    listener: OnFilterValueListener? = null,
    onActivityResult: (String?) -> Unit
  ) =
    launch(InputTextConfig(name, title, hint, value, listener), onActivityResult)
}