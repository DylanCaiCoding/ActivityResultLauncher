package com.dylanc.activityresult.launcher.sample.kotlin

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.dylanc.activityresult.launcher.*
import com.dylanc.activityresult.launcher.sample.R
import com.dylanc.activityresult.launcher.sample.databinding.ActivityLauncherBinding
import com.dylanc.activityresult.launcher.sample.kotlin.launcher.InputTextLauncher
import java.io.File


class KotlinSampleActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLauncherBinding
  private val takePictureLauncher = TakePictureLauncher(this)
  private val takeVideoLauncher = TakeVideoLauncher(this)
  private val pickContactLauncher = PickContactLauncher(this)
  private val takePicturePreviewLauncher = TakePicturePreviewLauncher(this)
  private val permissionLauncher = PermissionLauncher(this)
  private val getContentLauncher = GetContentLauncher(this)
  private val createDocumentLauncher = CreateDocumentLauncher(this)
  private val openMultipleDocumentLauncher = OpenMultipleDocumentsLauncher(this)
  private val openDocumentTreeLauncher = OpenDocumentTreeLauncher(this)
  private val cropPictureLauncher = CropPictureLauncher(this)
  private val inputTextLauncher = InputTextLauncher(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLauncherBinding.inflate(layoutInflater)
    setContentView(binding.root)
    with(binding) {
      btnTakePicture.setOnClickListener { takePicture() }
      btnTakePicturePreview.setOnClickListener { takePicturePreview() }
      btnSelectPicture.setOnClickListener { selectPicture() }
      btnTakeVideo.setOnClickListener { takeVideo() }
      btnPickContact.setOnClickListener { pickContact() }
      btnCreateDocument.setOnClickListener { createDocument() }
      btnOpenMultipleDocument.setOnClickListener { openMultipleDocument() }
      btnOpenDocumentTree.setOnClickListener { openDocumentTree() }
      btnInputText.setOnClickListener { inputText() }
    }
  }

  private fun takePicture() {
    val uri = uriOf("test.jpg")
    takePictureLauncher.launch(uri) {
      if (it) {
        cropPictureLauncher.launch(uri) { cropUri ->
          binding.ivPicture.setImageURI(cropUri)
        }
      }
    }
  }

  private fun takePicturePreview() {
    takePicturePreviewLauncher.launch {
      binding.ivPicture.setImageBitmap(it)
    }
  }

  private fun selectPicture() {
    getContentLauncher.launchForImage(
      onActivityResult = { uri ->
        if (uri != null) {
          binding.ivPicture.setImageURI(uri)
        }
      },
      onPermissionDenied = {
        Toast.makeText(application, R.string.no_read_permission, Toast.LENGTH_SHORT).show()
      }
    )
  }

  private fun takeVideo() {
    val uri = uriOf("test.mp4")
    takeVideoLauncher.launch(uri) {
      binding.ivPicture.setImageURI(uri)
    }
  }

  private fun pickContact() {
    pickContactLauncher.launch {

    }
  }

  private fun createDocument() {
    createDocumentLauncher.launch(null) {

    }
  }

  private fun openMultipleDocument() {
    openMultipleDocumentLauncher.launch(arrayOf()) {

    }
  }

  private fun openDocumentTree() {
    openDocumentTreeLauncher.launch(null) {

    }
  }

  private fun inputText() {
    inputTextLauncher.launch("name", "Input name") {
      if (!it.isNullOrEmpty())
        Toast.makeText(application, it, Toast.LENGTH_SHORT).show()
    }
  }

  private fun uriOf(filename: String): Uri {
    val file = File(externalCacheDir, filename)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      FileProvider.getUriForFile(application, "$packageName.provider", file)
    } else {
      Uri.fromFile(file)
    }
  }
}