package com.dylanc.activityresult.launcher

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import java.io.File

/**
 * @author Dylan Cai
 */

internal fun Uri.copyToCacheFile(context: Context): File =
  if (scheme == ContentResolver.SCHEME_CONTENT) {
    val contentResolver = context.contentResolver
    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(this))
    val fileName = contentResolver.query(
      this, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null
    )?.use { cursor ->
      if (cursor.moveToFirst())
        cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
      else null
    } ?: "${System.currentTimeMillis()}.$fileExtension"
    File("${context.externalCacheDir}/$fileName").apply {
      contentResolver.openInputStream(this@copyToCacheFile)?.use { inputStream ->
        outputStream().use { inputStream.copyTo(it) }
      }
    }
  } else {
    throw IllegalArgumentException("Uri lacks 'content' scheme.")
  }

internal fun File.toUri(context: Context) =
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    FileProvider.getUriForFile(context, "${context.packageName}.provider", this)
  } else {
    Uri.fromFile(this)
  }