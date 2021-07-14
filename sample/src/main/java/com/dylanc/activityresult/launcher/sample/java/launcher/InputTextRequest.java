package com.dylanc.activityresult.launcher.sample.java.launcher;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Dylan Cai
 */
public class InputTextRequest {
  @NonNull
  private final String name;
  @NonNull
  private final String title;
  @Nullable
  private final String value;

  public InputTextRequest(@NonNull String name, @NonNull String title, @Nullable String value) {
    this.name = name;
    this.title = title;
    this.value = value;
  }

  @NonNull
  public String getName() {
    return name;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  @Nullable
  public String getValue() {
    return value;
  }
}
