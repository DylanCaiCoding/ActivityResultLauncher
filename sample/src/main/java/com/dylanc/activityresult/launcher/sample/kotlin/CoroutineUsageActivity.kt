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

package com.dylanc.activityresult.launcher.sample.kotlin

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.OpenableColumns
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import com.dylanc.activityresult.launcher.*
import com.dylanc.activityresult.launcher.sample.BaseActivity
import com.dylanc.activityresult.launcher.sample.R
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextActivity
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextLauncher
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextResultContract.Companion.KEY_NAME
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextResultContract.Companion.KEY_TITLE
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextResultContract.Companion.KEY_VALUE
import com.dylanc.activityresult.launcher.sample.widget.PictureDialogFragment
import com.dylanc.viewbinding.binding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch


class CoroutineUsageActivity : BaseActivity() {

  private val binding: ActivityLauncherBinding by binding()
  private val startActivityLauncher = StartActivityLauncher(this)
  private val takePictureLauncher = TakePictureLauncher(this)
  private val takePicturePreviewLauncher = TakePicturePreviewLauncher(this)
  private val takeVideoLauncher = TakeVideoLauncher(this)
  private val pickContentLauncher = PickContentLauncher(this)
  private val getMultipleContentsLauncher = GetMultipleContentsLauncher(this)
  private val cropPictureLauncher = CropPictureLauncher(this)
  private val requestPermissionLauncher = RequestPermissionLauncher(this)
  private val requestMultiplePermissionsLauncher = RequestMultiplePermissionsLauncher(this)
  private val appDetailsSettingsLauncher = AppDetailsSettingsLauncher(this)
  private val createDocumentLauncher = CreateDocumentLauncher(this)
  private val openDocumentLauncher = OpenDocumentLauncher(this)
  private val openMultipleDocumentsLauncher = OpenMultipleDocumentsLauncher(this)
  private val openDocumentTreeLauncher = OpenDocumentTreeLauncher(this)
  private val pickContactLauncher = PickContactLauncher(this)
  private val enableBluetoothLauncher = EnableBluetoothLauncher(this)
  private val enableLocationLauncher = EnableLocationLauncher(this)
  private val inputTextLauncher = InputTextLauncher(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    with(binding) {
      btnStartActivity.setOnClickListener { startInputTextActivity() }
      btnTakePicturePreview.setOnClickListener { takePicturePreview() }
      btnTakePicture.setOnClickListener { takePicture() }
      btnCropPicture.setOnClickListener { takePictureAndCropIt() }
      btnTakeVideo.setOnClickListener { takeVideo() }
      btnPickPicture.setOnClickListener { pickPicture() }
      btnPickVideo.setOnClickListener { pickVideo() }
      btnGetMultiplePicture.setOnClickListener { getMultiplePicture() }
      btnGetMultipleVideo.setOnClickListener { getMultipleVideo() }
      btnRequestPermission.setOnClickListener { requestPermission() }
      btnRequestMultiplePermissions.setOnClickListener { requestMultiplePermissions() }
      btnAppDetailsSettings.setOnClickListener { goToAppDetailSettings() }
      btnCreateDocument.setOnClickListener { createDocument() }
      btnOpenDocument.setOnClickListener { openDocument() }
      btnOpenMultipleDocument.setOnClickListener { openMultipleDocuments() }
      btnOpenDocumentTree.setOnClickListener { openDocumentTree() }
      btnPickContact.setOnClickListener { pickContact() }
      btnEnableBluetooth.setOnClickListener { enableBluetooth() }
      btnEnableLocation.setOnClickListener { enableLocation() }
      btnInputText.setOnClickListener { inputText() }
    }
  }

  private fun startInputTextActivity() = lifecycleScope.launch {
    val activityResult = startActivityLauncher.launchForResult<InputTextActivity>(
      KEY_NAME to "nickname",
      KEY_TITLE to "Nickname",
    )
    if (activityResult.resultCode == RESULT_OK) {
      activityResult.data?.getStringExtra(KEY_VALUE)?.let { toast(it) }
    }
  }

  private fun takePicturePreview() = lifecycleScope.launch {
    val bitmap = takePicturePreviewLauncher.launchForResult()
    PictureDialogFragment(bitmap).show(supportFragmentManager)
  }

  private fun takePicture() = lifecycleScope.launch {
    val uri = takePictureLauncher.launchForResult()
    PictureDialogFragment(uri) {
      contentResolver.delete(uri, null, null)
    }.show(supportFragmentManager)
  }

  private fun takePictureAndCropIt() = lifecycleScope.launch {
    val uri = takePictureLauncher.launchForResult()
    val croppedUri = cropPictureLauncher.launchForResult(uri)
    contentResolver.delete(uri, null, null)
    PictureDialogFragment(croppedUri).show(supportFragmentManager)
  }

  private fun takeVideo() = lifecycleScope.launch {
    val uri = takeVideoLauncher.launchForResult()
    PictureDialogFragment(uri) {
      contentResolver.delete(uri, null, null)
    }.show(supportFragmentManager)
  }

  private fun pickPicture() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.READ_EXTERNAL_STORAGE,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.no_read_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_read_permission) {
            pickPicture()
          }
        })
        .transform {
          emit(pickContentLauncher.launchForImageResult())
        }
        .collect { uri ->
          PictureDialogFragment(uri).show(supportFragmentManager)
        }
    }
  }

  private fun pickVideo() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.READ_EXTERNAL_STORAGE,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.no_read_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_read_permission) {
            pickVideo()
          }
        })
        .transform {
          emit(pickContentLauncher.launchForVideoResult())
        }
        .collect { uri ->
          PictureDialogFragment(uri, true).show(supportFragmentManager)
        }
    }
  }

  private fun getMultiplePicture() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.READ_EXTERNAL_STORAGE,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.no_read_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_read_permission) {
            getMultiplePicture()
          }
        })
        .transform {
          emit(getMultipleContentsLauncher.launchForImageResult())
        }
        .collect { uris ->
          showItems(R.string.selected_files, uris.map { it.displayName!! })
        }
    }
  }

  private fun getMultipleVideo() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.READ_EXTERNAL_STORAGE,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.no_read_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_read_permission) {
            getMultipleVideo()
          }
        })
        .transform {
          emit(getMultipleContentsLauncher.launchForVideoResult())
        }
        .collect { uris ->
          showItems(R.string.selected_files, uris.map { it.displayName!! })
        }
    }
  }

  private fun requestPermission() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.ACCESS_FINE_LOCATION,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.no_location_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_location_permission) {
            requestPermission()
          }
        })
        .collect {
          toast(R.string.location_permission_granted)
        }
    }
  }

  private fun requestMultiplePermissions() {
    lifecycleScope.launch {
      requestMultiplePermissionsLauncher.launchForFlow(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        onDenied = { list, settingsLauncher ->
          val message = when {
            list.size == 2 -> R.string.need_location_and_read_permissions
            list.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
            else -> R.string.need_read_permission
          }
          showDialog(R.string.need_permission_title, message) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = { list ->
          val message = when {
            list.size == 2 -> R.string.need_location_and_read_permissions
            list.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
            else -> R.string.need_read_permission
          }
          showDialog(R.string.need_permission_title, message) {
            requestMultiplePermissions()
          }
        }
      ).collect {
        toast(R.string.location_and_read_permissions_granted)
      }
    }
  }

  private fun goToAppDetailSettings() = lifecycleScope.launch {
    appDetailsSettingsLauncher.launchForResult()
  }

  private fun enableBluetooth() {
    lifecycleScope.launch {
      requestPermissionLauncher.launchForFlow(Manifest.permission.ACCESS_FINE_LOCATION,
        onDenied = { settingsLauncher ->
          showDialog(R.string.need_permission_title, R.string.bluetooth_need_location_permission) {
            settingsLauncher.launch()
          }
        },
        onExplainRequest = {
          showDialog(R.string.need_permission_title, R.string.need_location_permission) {
            enableBluetooth()
          }
        })
        .transform {
          val bluetoothEnabled = enableBluetoothLauncher.launchForResult()
          if (bluetoothEnabled && !isLocationEnabled) {
            toast(R.string.enable_location_reason)
            if (enableLocationLauncher.launchForResult()) {
              emit(Unit)
            }
          } else if (bluetoothEnabled) {
            emit(Unit)
          }
        }
        .collect {
          toast(R.string.bluetooth_enabled)
        }
    }
  }

  private fun enableLocation() = lifecycleScope.launch {
    if (enableLocationLauncher.launchForResult()) {
      toast(R.string.location_enabled)
    } else {
      toast(R.string.location_disabled)
    }
  }

  private fun createDocument() = lifecycleScope.launch {
    val uri = createDocumentLauncher.launchForResult()
    contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
      ?.use { cursor ->
        if (cursor.moveToFirst()) {
          val name =
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
          toast("create $name")
        }
      }
  }

  private fun openDocument() = lifecycleScope.launch {
    val uri = openDocumentLauncher.launchForResult("application/*")
    contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
      ?.use { cursor ->
        if (cursor.moveToFirst()) {
          val name =
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
          toast(name)
        }
      }
  }

  private fun openMultipleDocuments() = lifecycleScope.launch {
    val uris = openMultipleDocumentsLauncher.launchForNonEmptyResult("application/*")
    val names = uris.map {
      contentResolver.query(it, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        ?.use { cursor ->
          cursor.moveToFirst()
          cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        }.orEmpty()
    }
    showItems(R.string.documents, names)
  }

  private fun openDocumentTree() = lifecycleScope.launch {
    val uri = openDocumentTreeLauncher.launchForResult()
    val documentFile = DocumentFile.fromTreeUri(this@CoroutineUsageActivity, uri)!!
    val names = documentFile.listFiles().map { it.name.orEmpty() }
    showItems(R.string.documents, names)
  }

  private fun pickContact() = lifecycleScope.launch {
    val uri = pickContactLauncher.launchForResult()
    contentResolver.query(uri, arrayOf(ContactsContract.Data.DISPLAY_NAME), null, null, null)
      ?.use { cursor ->
        if (cursor.moveToFirst()) {
          val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))
          toast(name)
        }
      }
  }

  private fun inputText() = lifecycleScope.launch {
    val value = inputTextLauncher.launchForResult(name = "name", title = "Input name")
    toast(value)
  }
}