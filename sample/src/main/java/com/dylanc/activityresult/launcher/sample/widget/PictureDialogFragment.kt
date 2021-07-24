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
import com.dylanc.callbacks.Callback0
import java.util.*

/**
 * @author Dylan Cai
 */
class PictureDialogFragment(private val onLoadPicture: PictureDialog.(DialogPictureBinding) -> Unit) : DialogFragment() {

  @JvmOverloads
  constructor(uri: Uri, isVideo: Boolean = false, listener: Callback0? = null) : this({
    Glide.with(context).load(uri).centerCrop().into(it.ivPicture)
    listener?.let { setOnDismissListener { listener.invoke() } }
    it.ivVideo.isVisible = isVideo
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
        onLoadPicture(this@PictureDialog, this)
      }
    }
  }

  companion object {
    const val TAG = "picture_dialog"
  }
}