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

package com.dylanc.activityresult.launcher;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseActivityResultLauncher<I, O> {

  private final ActivityResultLauncher<I> launcher;
  private ActivityResultCallback<O> callback;

  public BaseActivityResultLauncher(ActivityResultCaller caller, ActivityResultContract<I, O> contract) {
    launcher = caller.registerForActivityResult(contract, (result) -> {
      if (callback != null) {
        callback.onActivityResult(result);
        callback = null;
      }
    });
  }

  public void launch(@Nullable I input, @NonNull ActivityResultCallback<O> callback) {
    this.callback = callback;
    launcher.launch(input);
  }
}