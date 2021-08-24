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

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author Dylan Cai
 */
@SuppressWarnings("unused")
public class BaseActivityResultLauncher<I, O> {

  private final androidx.activity.result.ActivityResultLauncher<I> launcher;
  private final ActivityResultCaller caller;
  private ActivityResultCallback<O> callback;
  private MutableLiveData<O> unprocessedResult;

  public BaseActivityResultLauncher(@NonNull ActivityResultCaller caller, @NonNull ActivityResultContract<I, O> contract) {
    this.caller = caller;
    launcher = caller.registerForActivityResult(contract, (result) -> {
      if (callback != null) {
        callback.onActivityResult(result);
        callback = null;
      } else {
        getUnprocessedResultLiveData().setValue(result);
      }
    });
  }

  public void launch(@SuppressLint("UnknownNullness") I input, @NonNull ActivityResultCallback<O> callback) {
    launch(input, null, callback);
  }

  public void launch(@SuppressLint("UnknownNullness") I input, @Nullable ActivityOptionsCompat options,
                     @NonNull ActivityResultCallback<O> callback) {
    this.callback = callback;
    launcher.launch(input, options);
  }

  public LiveData<O> getUnprocessedResult() {
    return getUnprocessedResultLiveData();
  }

  public Context getContext() {
    return ActivityResultCallerKt.getContext(caller);
  }

  private MutableLiveData<O> getUnprocessedResultLiveData() {
    if (unprocessedResult == null) {
      unprocessedResult = new MutableLiveData<>();
    }
    return unprocessedResult;
  }
}