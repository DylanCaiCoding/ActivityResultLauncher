package com.dylanc.activityresult.launcher.sample.java;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.dylanc.activityresult.launcher.RequestPermissionLauncher;
import com.dylanc.activityresult.launcher.StartActivityLauncher;
import com.dylanc.activityresult.launcher.TakePictureLauncher;
import com.dylanc.activityresult.launcher.sample.MainActivity;
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/**
 * @author Dylan Cai
 */
public class JavaSampleActivity extends AppCompatActivity {

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
    Uri uri = getUri("test.jpg");
    takePictureLauncher.launch(uri,
        (takeSuccess) -> {
          if (takeSuccess) {
            binding.ivPicture.setImageURI(uri);
          }
        });
  }

  private Uri getUri(String filename) {
    File file = new File(getExternalCacheDir(), filename);
    Uri uri;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
    } else {
      uri = Uri.fromFile(file);
    }
    return uri;
  }
}