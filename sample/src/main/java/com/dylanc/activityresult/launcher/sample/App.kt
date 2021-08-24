package com.dylanc.activityresult.launcher.sample

import android.app.Application
import com.dylanc.activityresult.launcher.FileProviderUtils

/**
 * @author Dylan Cai
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    FileProviderUtils.authority = "$packageName.fileProvider"
  }
}