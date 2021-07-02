package com.dylanc.activityresult.launcher.sample.java;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.dylanc.activityresult.launcher.RequestPermissionLauncher;
import com.dylanc.activityresult.launcher.StartActivityLauncher;
import com.dylanc.activityresult.launcher.TakePictureLauncher;
import com.dylanc.activityresult.launcher.sample.base.BaseActivity;
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding;

/**
 * @author Dylan Cai
 */
public class JavaSampleActivity extends BaseActivity {

  private ActivityLauncherBinding binding;
  private final TakePictureLauncher takePictureLauncher = new TakePictureLauncher(this);
  private final RequestPermissionLauncher requestPermissionLauncher = new RequestPermissionLauncher(this);
  private final StartActivityLauncher startActivityLauncher = new StartActivityLauncher(this);

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLauncherBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.btnTakePicture.setOnClickListener(this::takePicture);
  }

  private void takePicture(View view) {
    takePictureLauncher.launch((uri, file) -> {
      if (uri != null && file != null) {

      }
    });
  }
}

