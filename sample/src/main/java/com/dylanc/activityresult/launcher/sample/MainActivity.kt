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

package com.dylanc.activityresult.launcher.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    menuInflater.inflate(R.menu.github, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.github) {
      val uri = Uri.parse("https://github.com/DylanCaiCoding/ActivityResultLauncher")
      val intent = Intent("android.intent.action.VIEW", uri)
      startActivity(intent)
    }
    return true
  }
}