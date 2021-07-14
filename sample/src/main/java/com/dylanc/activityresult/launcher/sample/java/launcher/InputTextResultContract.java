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