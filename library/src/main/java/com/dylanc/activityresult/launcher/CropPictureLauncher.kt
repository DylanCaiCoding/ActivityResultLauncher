@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.activityresult.launcher

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment


inline fun ComponentActivity.CropPictureLauncher() = CropPictureLauncher(this)

inline fun Fragment.CropPictureLauncher() = CropPictureLauncher(this)

data class CropPictureConfig(
  val inputUri: Uri,
  val outputUri: Uri,
  val aspectX: Int = 1,
  val aspectY: Int = 1,
  val outputX: Int = 512,
  val outputY: Int = 512,
  val isScale: Boolean = true,
  val isScaleUpIfNeeded: Boolean = false
)

class CropPictureLauncher(caller: ActivityResultCaller) :
  BaseActivityResultLauncher<CropPictureConfig, Uri>(caller, CropPictureContract()) {

  fun launch(
    inputUri: Uri, outputUri: Uri, aspectX: Int = 1, aspectY: Int = 1, outputX: Int = 512,
    outputY: Int = 512, isScale: Boolean = true, isScaleUpIfNeeded: Boolean = true, block: (Uri) -> Unit
  ) =
    launch(CropPictureConfig(inputUri, outputUri, aspectX, aspectY, outputX, outputY, isScale, isScaleUpIfNeeded), block)
}

class CropPictureContract : ActivityResultContract<CropPictureConfig, Uri>() {
  private lateinit var outputUri: Uri

  @CallSuper
  override fun createIntent(context: Context, input: CropPictureConfig): Intent {
    return Intent("com.android.camera.action.CROP")
      .apply {
        setDataAndType(input.inputUri, "image/*")
        putExtra(MediaStore.EXTRA_OUTPUT, input.outputUri)
        putExtra("aspectX", input.aspectX)
        putExtra("aspectY", input.aspectY)
        putExtra("outputX", input.outputX)
        putExtra("outputY", input.outputY)
        putExtra("scale", input.isScale)
        putExtra("scaleUpIfNeeded", input.isScaleUpIfNeeded)
        putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        putExtra("return-data", false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        outputUri = input.outputUri
      }
  }

  override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
    if (resultCode == Activity.RESULT_OK) outputUri else null

}
