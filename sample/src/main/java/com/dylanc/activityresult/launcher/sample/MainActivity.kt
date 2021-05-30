package com.dylanc.activityresult.launcher.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dylanc.activityresult.launcher.TakePictureLauncher

class MainActivity : AppCompatActivity() {

  private val takePictureLauncher = TakePictureLauncher()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}