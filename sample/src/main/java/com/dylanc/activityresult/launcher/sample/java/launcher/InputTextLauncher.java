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

package com.dylanc.activityresult.launcher.sample.java.launcher;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dylanc.activityresult.launcher.BaseActivityResultLauncher;


/**
 * @author Dylan Cai
 */
public class InputTextLauncher extends BaseActivityResultLauncher<InputTextRequest, String> {

  public InputTextLauncher(@NonNull ActivityResultCaller caller) {
    super(caller, new InputTextResultContract());
  }

  public void launch(@NonNull String name, @NonNull String title, @NonNull ActivityResultCallback<String> callback) {
    launch(name, title, null, callback);
  }

  public void launch(@NonNull String name, @NonNull String title,
                     @Nullable String value, @NonNull ActivityResultCallback<String> callback) {
    launch(new InputTextRequest(name, title, value), callback);
  }
}