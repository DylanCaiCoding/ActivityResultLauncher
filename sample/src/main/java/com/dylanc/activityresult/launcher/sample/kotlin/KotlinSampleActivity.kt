package com.dylanc.activityresult.launcher.sample.kotlin

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.dylanc.activityresult.launcher.*
import com.dylanc.activityresult.launcher.sample.R
import com.dylanc.activityresult.launcher.sample.base.BaseActivity
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextLauncher


class KotlinSampleActivity : BaseActivity() {

  private lateinit var binding: ActivityLauncherBinding
  private val requestPermissionLauncher = RequestPermissionLauncher(this)
  private val requestMultiplePermissionsLauncher = RequestMultiplePermissionsLauncher(this)
  private val takePictureLauncher = TakePictureLauncher(this)
  private val takePicturePreviewLauncher = TakePicturePreviewLauncher(this)
  private val getContentLauncher = GetContentLauncher(this)
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
//  private val startActivityLauncher = StartActivityLauncher(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLauncherBinding.inflate(layoutInflater)
    setContentView(binding.root)
    with(binding) {
      btnRequestPermission.setOnClickListener { requestPermission() }
      btnRequestMultiplePermissions.setOnClickListener { requestMultiplePermissions() }
      btnTakePicture.setOnClickListener { takePicture() }
      btnTakePicturePreview.setOnClickListener { takePicturePreview() }
      btnCropPicture.setOnClickListener { takePictureAndCropIt() }
      btnTakeVideo.setOnClickListener { takeVideo() }
      btnSelectPicture.setOnClickListener { selectPicture() }
      btnSelectVideo.setOnClickListener { selectVideo() }
      btnSelectAudio.setOnClickListener { selectAudio() }
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
        toast(getString(R.string.location_permission_granted))
      },
      onDenied = {
        toast(getString(R.string.location_permission_denied))
      },
      onExplainRequest = {
        showDialog(getString(R.string.Tip), getString(R.string.no_location_permission)) {
          requestMultiplePermissions()
        }
      }
    )
  }

  private fun requestMultiplePermissions() {
    requestMultiplePermissionsLauncher.launch(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      onAllGranted = {
        toast(getString(R.string.location_and_read_permissions_granted))
      },
      onDenied = {
        val message = when {
          it.size == 2 -> R.string.need_location_and_read_permissions
          it.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
          else -> R.string.need_read_permission
        }
        toast(getString(message))
      },
      onExplainRequest = {
        val message = when {
          it.size == 2 -> R.string.need_location_and_read_permissions
          it.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
          else -> R.string.need_read_permission
        }
        showDialog(getString(R.string.Tip), getString(message)) {
          requestMultiplePermissions()
        }
      }
    )
  }

  private fun takePicture() {
    takePictureLauncher.launch { uri, file ->
      if (uri != null && file != null) {
        toast(file.path)
      }
    }
  }

  private fun takePicturePreview() {
    takePicturePreviewLauncher.launch {
//      binding.ivPicture.setImageBitmap(it)
    }
  }

  private fun takePictureAndCropIt() {
    takePictureLauncher.launch { uri, file ->
      if (uri != null) {
        cropPictureLauncher.launch(uri) { _, croppedFile ->
          file?.delete()
          if (croppedFile != null) {
            toast(croppedFile.path)
            croppedFile.delete()
          }
        }
      }
    }
  }

  private fun takeVideo() {
    takeVideoLauncher.launch { uri, file ->
      if (uri != null && file != null) {
        toast(file.path)
        file.delete()
      }
    }
  }

  private fun selectPicture() {
    getContentLauncher.launchForImage(
      onActivityResult = { uri, file ->
        if (uri != null && file != null) {
          toast(file.path)
          file.delete()
        }
      },
      onPermissionDenied = {
        Toast.makeText(this, R.string.no_read_permission, Toast.LENGTH_SHORT).show()
      }
    )
  }

  private fun selectVideo() {
    getContentLauncher.launchForVideo(
      onActivityResult = { uri, file ->
        if (uri != null && file != null) {
          toast(file.path)
          file.delete()
        }
      },
      onPermissionDenied = {
        Toast.makeText(this, R.string.no_read_permission, Toast.LENGTH_SHORT).show()
      }
    )
  }

  private fun selectAudio() {
    getContentLauncher.launchForAudio(
      onActivityResult = { uri, file ->
        if (uri != null && file != null) {
          toast(file.path)
          file.delete()
        }
      },
      onPermissionDenied = {
        Toast.makeText(this, R.string.no_read_permission, Toast.LENGTH_SHORT).show()
      }
    )
  }

  private fun createDocument() {
    createDocumentLauncher.launch(null) {
      if (it != null) {
        Toast.makeText(this, it.path, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun openDocument() {
    openDocumentLauncher.launch(arrayOf()) {
      if (it != null) {
        Toast.makeText(this, it.path, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun openMultipleDocuments() {
    openMultipleDocumentsLauncher.launch(arrayOf()) {
      if (it.isNullOrEmpty()) {
        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun openDocumentTree() {
    openDocumentTreeLauncher.launch(null) {
      if (it != null) {
        Toast.makeText(this, it.path, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun pickContact() {
    pickContactLauncher.launch {
      if (it != null) {
        val projection = arrayOf(ContactsContract.Data.DISPLAY_NAME)
        contentResolver.query(it, projection, null, null, null)?.use { cursor ->
          if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
            val name = cursor.getString(nameIndex)
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

  private fun enableBluetooth() {
    enableBluetoothLauncher.launchAndEnableLocation(
      R.string.enable_location_reason,
      onLocationEnabled = { enabled ->
        if (enabled) {
          Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show()
        }
      },
      onPermissionDenied = {
        Toast.makeText(this, "Please grant location permission to use Bluetooth", Toast.LENGTH_SHORT).show()
      },
//      onLocationDisabled = {
//        showDialog(getString(R.string.Tip), getString(R.string.enable_location_reason)) {
//          it.launch { enableBluetooth() }
//        }
//      }
    )
  }

  private fun enableLocation() {
    enableLocationLauncher.launch {
      if (it) {
        toast("Location is enabled")
      } else {
        toast("Location is disabled")
      }
    }
  }


  private fun inputText() {
    inputTextLauncher.launch("name", "Input name") {
      if (it != null) {
        toast(it)
      }
    }
  }
}