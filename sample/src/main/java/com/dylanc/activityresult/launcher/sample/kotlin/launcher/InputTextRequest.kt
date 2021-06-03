package com.dylanc.activityresult.launcher.sample.kotlin.launcher

import java.io.Serializable

/**
 * @author Dylan Cai
 */


interface OnFilterValueListener : Serializable {
  fun onFilterValue(value: String): Boolean
}

class InputTextRequest(
  val name: String,
  val title: String = name,
  val hint: String? = null,
  val value: String? = null,
  val listener: OnFilterValueListener? = null
)