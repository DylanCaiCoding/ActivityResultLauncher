/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.activityresult.launcher

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.*
import android.content.IntentSender
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.annotation.IntDef
import com.dylanc.callbacks.Callback2

/**
 * @author Dylan Cai
 */
class StartIntentSenderLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<IntentSenderRequest, ActivityResult>(caller, StartIntentSenderForResult()) {

  @JvmOverloads
  fun launch(
    intentSender: IntentSender,
    fillInIntent: Intent? = null,
    @Flag flagsValues: Int = 0,
    flagsMask: Int = 0,
    onActivityResult: Callback2<Int, Intent?>
  ) =
    launch(IntentSenderRequest(intentSender, fillInIntent, flagsValues, flagsMask)) {
      onActivityResult(it.resultCode, it.data)
    }

  suspend fun launchForResult(
    intentSender: IntentSender,
    fillInIntent: Intent? = null,
    @Flag flagsValues: Int = 0,
    flagsMask: Int = 0
  ) =
    launchForResult(IntentSenderRequest(intentSender, fillInIntent, flagsValues, flagsMask))

  private fun IntentSenderRequest(
    intentSender: IntentSender,
    fillInIntent: Intent? = null,
    @Flag flagsValues: Int = 0,
    flagsMask: Int = 0
  ) = IntentSenderRequest.Builder(intentSender)
    .setFillInIntent(fillInIntent)
    .setFlags(flagsValues, flagsMask)
    .build()

  @SuppressLint("InlinedApi")
  @IntDef(
    FLAG_GRANT_READ_URI_PERMISSION, FLAG_GRANT_WRITE_URI_PERMISSION,
    FLAG_FROM_BACKGROUND, FLAG_DEBUG_LOG_RESOLUTION, FLAG_EXCLUDE_STOPPED_PACKAGES,
    FLAG_INCLUDE_STOPPED_PACKAGES, FLAG_GRANT_PERSISTABLE_URI_PERMISSION,
    FLAG_GRANT_PREFIX_URI_PERMISSION, FLAG_ACTIVITY_MATCH_EXTERNAL,
    FLAG_ACTIVITY_NO_HISTORY, FLAG_ACTIVITY_SINGLE_TOP, FLAG_ACTIVITY_NEW_TASK,
    FLAG_ACTIVITY_MULTIPLE_TASK, FLAG_ACTIVITY_CLEAR_TOP,
    FLAG_ACTIVITY_FORWARD_RESULT, FLAG_ACTIVITY_PREVIOUS_IS_TOP,
    FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS, FLAG_ACTIVITY_BROUGHT_TO_FRONT,
    FLAG_ACTIVITY_RESET_TASK_IF_NEEDED, FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY,
    FLAG_ACTIVITY_NEW_DOCUMENT, FLAG_ACTIVITY_NO_USER_ACTION,
    FLAG_ACTIVITY_REORDER_TO_FRONT, FLAG_ACTIVITY_NO_ANIMATION,
    FLAG_ACTIVITY_CLEAR_TASK, FLAG_ACTIVITY_TASK_ON_HOME,
    FLAG_ACTIVITY_RETAIN_IN_RECENTS, FLAG_ACTIVITY_LAUNCH_ADJACENT
  )
  @Retention(AnnotationRetention.SOURCE)
  private annotation class Flag
}