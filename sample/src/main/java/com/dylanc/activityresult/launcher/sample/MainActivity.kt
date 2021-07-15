package com.dylanc.activityresult.launcher.sample

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.activityresult.launcher.sample.databinding.ActivityMainBinding
import com.dylanc.activityresult.launcher.sample.java.JavaSampleActivity
import com.dylanc.activityresult.launcher.sample.kotlin.KotlinSampleActivity
import com.dylanc.viewbinding.binding

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.btnKotlin.setOnClickListener {
      startActivity(Intent(this, KotlinSampleActivity::class.java))
    }
    binding.btnJava.setOnClickListener {
      startActivity(Intent(this, JavaSampleActivity::class.java))
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    return super.onCreateOptionsMenu(menu)
  }
}