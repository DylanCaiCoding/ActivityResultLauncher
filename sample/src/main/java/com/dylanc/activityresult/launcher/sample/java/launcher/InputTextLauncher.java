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