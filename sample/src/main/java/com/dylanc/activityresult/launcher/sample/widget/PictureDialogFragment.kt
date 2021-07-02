package com.dylanc.activityresult.launcher.sample.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.dylanc.activityresult.launcher.sample.R
import com.dylanc.activityresult.launcher.sample.databinding.DialogPictureBinding
import java.io.File
import java.util.*

/**
 * @author Dylan Cai
 */
class PictureDialogFragment(private val onLoadPicture: PictureDialog.(DialogPictureBinding) -> Unit) : DialogFragment() {

  constructor(uri: Uri, file: File) : this({
    it.tvPath.text = "path: ${file.path}"
    Glide.with(context).load(uri).centerCrop().into(it.ivPicture)
    setOnDismissListener { file.delete() }
    it.ivVideo.isVisible = file.path.lowercase(Locale.getDefault()).endsWith("mp4")
  })

  constructor(bitmap: Bitmap) : this({
    Glide.with(context).load(bitmap).centerCrop().into(it.ivPicture)
  })

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    PictureDialog(requireContext())

  fun show(manager: FragmentManager) {
    super.show(manager, TAG)
  }

  inner class PictureDialog(context: Context) :
    Dialog(context, R.style.TranslucentBgDialogTheme) {

    private lateinit var binding: DialogPictureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DialogPictureBinding.inflate(layoutInflater)
      setContentView(binding.root)
      with(binding) {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        root.setOnClickListener { this@PictureDialogFragment.dismiss() }
        onLoadPicture(this@PictureDialog,this)
      }
    }
  }

  companion object {
    const val TAG = "picture_dialog"
  }
}