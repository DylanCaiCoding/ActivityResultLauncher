package com.dylanc.activityresult.launcher

import android.app.Activity
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

/**
 * @author Dylan Cai
 */
class DeleteUriLauncher {


  inline fun Activity.delete(contentUri: Uri, id: Long = ContentUris.parseId(contentUri)) {
    val requestCode = 100
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
        val pendingIntent = MediaStore.createDeleteRequest(contentResolver, listOf(contentUri))
        startIntentSenderForResult(
          pendingIntent.intentSender, requestCode, null,
          0, 0, 0, null
        )
      }
      Build.VERSION.SDK_INT == Build.VERSION_CODES.Q -> {
        try {
          val selection = "${MediaStore.MediaColumns._ID} = ?"
          val selectionArgs = arrayOf(id.toString())
          contentResolver.delete(contentUri, selection, selectionArgs)
        } catch (ex: RecoverableSecurityException) {
          val intent = ex.userAction.actionIntent.intentSender
          startIntentSenderForResult(
            intent, requestCode, null,
            0, 0, 0, null
          )
        }
      }
      else -> {
        val selection = "${MediaStore.MediaColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        contentResolver.delete(contentUri, selection, selectionArgs)
      }
    }
  }

}