package com.dylanc.activityresult.launcher.sample.kotlin

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.OpenableColumns
import androidx.documentfile.provider.DocumentFile
import com.dylanc.activityresult.launcher.*
import com.dylanc.activityresult.launcher.sample.BaseActivity
import com.dylanc.activityresult.launcher.sample.R
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextLauncher
import com.dylanc.activityresult.launcher.sample.widget.PictureDialogFragment
import com.dylanc.viewbinding.binding


class KotlinSampleActivity : BaseActivity() {

  private val binding: ActivityLauncherBinding by binding()
  private val requestPermissionLauncher = RequestPermissionLauncher(this)
  private val requestMultiplePermissionsLauncher = RequestMultiplePermissionsLauncher(this)
  private val takePictureLauncher = TakePictureLauncher(this)
  private val takePicturePreviewLauncher = TakePicturePreviewLauncher(this)
  private val pickContentLauncher = PickContentLauncher(this)
  private val getMultipleContentsLauncher = GetMultipleContentsLauncher(this)
  private val cropPictureLauncher = CropPictureLauncher(this)
  private val takeVideoLauncher = TakeVideoLauncher(this)
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
      btnRequestPermission.setOnClickListener { requestPermission() }
      btnRequestMultiplePermissions.setOnClickListener { requestMultiplePermissions() }
      btnTakePicturePreview.setOnClickListener { takePicturePreview() }
      btnTakePicture.setOnClickListener { takePicture() }
      btnCropPicture.setOnClickListener { takePictureAndCropIt() }
      btnTakeVideo.setOnClickListener { takeVideo() }
      btnPickPicture.setOnClickListener { pickPicture() }
      btnPickVideo.setOnClickListener { pickVideo() }
      btnGetMultiplePicture.setOnClickListener { getMultiplePicture() }
      btnGetMultipleVideo.setOnClickListener { getMultipleVideo() }
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

  private fun requestPermission() {
    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION,
      onGranted = {
        toast(R.string.location_permission_granted)
      },
      onDenied = {
        toast(R.string.no_location_permission)
      },
      onExplainRequest = {
        showDialog(R.string.need_permission_title, R.string.need_location_permission) {
          requestPermission()
        }
      }
    )
  }

  private fun requestMultiplePermissions() {
    requestMultiplePermissionsLauncher.launch(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onAllGranted = {
        toast(R.string.location_and_read_permissions_granted)
      },
      onDenied = { list ->
        val message = when {
          list.size == 2 -> R.string.need_location_and_read_permissions
          list.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
          else -> R.string.need_read_permission
        }
        toast(message)
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
    )
  }

  private fun takePicturePreview() {
    takePicturePreviewLauncher.launch { bitmap ->
      if (bitmap != null) {
        PictureDialogFragment(bitmap).show(supportFragmentManager)
      }
    }
  }

  private fun takePicture() {
    takePictureLauncher.launch { uri, file ->
      if (uri != null && file != null) {
        PictureDialogFragment(uri, file).show(supportFragmentManager)
      }
    }
  }

  private fun takePictureAndCropIt() {
    takePictureLauncher.launch { uri, file ->
      if (uri != null && file != null) {
        cropPictureLauncher.launch(uri) { croppedUri, croppedFile ->
          file.delete()
          if (croppedUri != null && croppedFile != null) {
            PictureDialogFragment(croppedUri, croppedFile).show(supportFragmentManager)
          }
        }
      }
    }
  }

  private fun takeVideo() {
    takeVideoLauncher.launch { uri, file ->
      if (uri != null && file != null) {
        PictureDialogFragment(uri, file).show(supportFragmentManager)
      }
    }
  }

  private fun pickPicture() {
    pickContentLauncher.launchForImage(
      onActivityResult = { uri, file ->
        if (uri != null && file != null) {
          PictureDialogFragment(uri, file).show(supportFragmentManager)
        }
      },
      onPermissionDenied = {
        toast(R.string.no_read_permission)
      },
      onExplainRequestPermission = {
        showDialog(R.string.need_permission_title, R.string.need_read_permission) {
          pickPicture()
        }
      }
    )
  }

  private fun pickVideo() {
    pickContentLauncher.launchForVideo(
      onActivityResult = { uri, file ->
        if (uri != null && file != null) {
          PictureDialogFragment(uri, file).show(supportFragmentManager)
        }
      },
      onPermissionDenied = {
        toast(R.string.no_read_permission)
      },
      onExplainRequestPermission = {
        showDialog(R.string.need_permission_title, R.string.need_read_permission) {
          pickPicture()
        }
      }
    )
  }

  private fun getMultiplePicture() {
    getMultipleContentsLauncher.launchForImage(
      onActivityResult = { _, files ->
        if (files.isNotEmpty()) {
          showItems(R.string.selected_files, files.map { it.name })
        }
      },
      onPermissionDenied = {
        toast(R.string.no_read_permission)
      },
      onExplainRequestPermission = {
        showDialog(R.string.need_permission_title, R.string.need_read_permission) {
          getMultiplePicture()
        }
      }
    )
  }

  private fun getMultipleVideo() {
    getMultipleContentsLauncher.launchForVideo(
      onActivityResult = { _, files ->
        if (files.isNotEmpty()) {
          showItems(R.string.selected_files, files.map { it.name })
        }
      },
      onPermissionDenied = {
        toast(R.string.no_read_permission)
      },
      onExplainRequestPermission = {
        showDialog(R.string.need_permission_title, R.string.need_read_permission) {
          getMultipleVideo()
        }
      }
    )
  }

  private fun enableBluetooth() {
    enableBluetoothLauncher.launchAndEnableLocation(
      R.string.enable_location_reason,
      onLocationEnabled = { enabled ->
        if (enabled) {
          toast(R.string.bluetooth_enabled)
        }
      },
      onPermissionDenied = {
        toast(R.string.bluetooth_need_location_permission)
      },
      onExplainRequestPermission = {
        showDialog(R.string.need_permission_title, R.string.need_location_permission) {
          enableBluetooth()
        }
      }
    )
  }

  private fun enableLocation() {
    enableLocationLauncher.launch { enabled ->
      if (enabled) {
        toast(R.string.location_enabled)
      } else {
        toast(R.string.location_disabled)
      }
    }
  }

  private fun createDocument() {
    createDocumentLauncher.launch { uri ->
      if (uri != null) {
        contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            toast("create $name")
          }
        }
      }
    }
  }

  private fun openDocument() {
    openDocumentLauncher.launch("application/*") { uri ->
      if (uri != null) {
        contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            toast(name)
          }
        }
      }
    }
  }

  private fun openMultipleDocuments() {
    openMultipleDocumentsLauncher.launch("application/*") { uris ->
      if (uris.isNotEmpty()) {
        val names = uris.map {
          contentResolver.query(it, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
            cursor.moveToFirst()
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
          }.orEmpty()
        }
        showItems(R.string.documents, names)
      }
    }
  }


  private fun openDocumentTree() {
    openDocumentTreeLauncher.launch { uri ->
      if (uri != null) {
        val documentFile = DocumentFile.fromTreeUri(this, uri)!!
        val names = documentFile.listFiles().map { it.name.orEmpty() }
        showItems(R.string.documents, names)
      }
    }
  }

  private fun pickContact() {
    pickContactLauncher.launch { uri ->
      if (uri != null) {
        contentResolver.query(uri, arrayOf(ContactsContract.Data.DISPLAY_NAME), null, null, null)?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))
            toast(name)
          }
        }
      }
    }
  }

  private fun inputText() {
    inputTextLauncher.launch(name = "name", title = "Input name") { value ->
      if (value != null) {
        toast(value)
      }
    }
  }
}