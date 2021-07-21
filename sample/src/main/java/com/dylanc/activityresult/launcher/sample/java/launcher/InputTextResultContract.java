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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class InputTextResultContract extends ActivityResultContract<InputTextRequest, String> {
  public static final String KEY_NAME = "name";
  public static final String KEY_TITLE = "title";
  public static final String KEY_VALUE = "value";

  @NotNull
  @Override
  public Intent createIntent(@NonNull Context context, InputTextRequest input) {
    Intent intent = new Intent(context, InputTextActivity.class);
    intent.putExtra(KEY_NAME, input.getName());
    intent.putExtra(KEY_TITLE, input.getTitle());
    intent.putExtra(KEY_VALUE, input.getValue());
    return intent;
  }

  @Override
  public String parseResult(int resultCode, @Nullable Intent intent) {
    if (resultCode == Activity.RESULT_OK && intent != null) {
      return intent.getStringExtra(KEY_VALUE);
    }
    return null;
  }
}