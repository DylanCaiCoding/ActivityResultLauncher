package com.dylanc.activityresult.launcher.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.activityresult.launcher.sample.databinding.ActivityMainBinding
import com.dylanc.activityresult.launcher.sample.java.JavaSampleActivity
import com.dylanc.activityresult.launcher.sample.kotlin.KotlinSampleActivity

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    with(binding) {
      btnKotlin.setOnClickListener {
        startActivity(Intent(this@MainActivity, KotlinSampleActivity::class.java))
      }
      btnJava.setOnClickListener {
        startActivity(Intent(this@MainActivity, JavaSampleActivity::class.java))
      }
    }

  }
}